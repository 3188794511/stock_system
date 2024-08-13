package com.lj;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lj.stock.StockApplication;
import com.lj.stock.listener.SampleDataListener;
import com.lj.stock.model.Sample;
import com.lj.stock.service.SampleService;
import com.lj.stock.utils.PoiUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = StockApplication.class)
@Slf4j
public class StockApplicationTest {
    @Autowired
    private SampleService sampleService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static String fileName = "C:\\Users\\李敬\\Desktop\\入库单.xlsx";
    private static String fileName1 = "C:\\Users\\李敬\\Desktop\\测试数据.xlsx";

    @Test
    void loadData() {
        // 使用EasyExcel读取文件
        // 定义excel读取的监听器
        AnalysisEventListener<LinkedHashMap<String,String>> listener = new AnalysisEventListener<>() {
            @Override
            public void invoke(LinkedHashMap<String, String> data, AnalysisContext context) {
                // 打印每一行的数据
                System.out.println("Row data: " + data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 所有数据解析完成后执行的操作（可选）
            }
        };

        // 使用EasyExcel读取文件
        EasyExcel.read(fileName1, listener).sheet().doRead();
    }

    @Test
    void loadData1() {
        EasyExcel.read(fileName, Sample.class, new SampleDataListener(sampleService))
                .headRowNumber(3).sheet(0).doRead();


    }

    @Test
    void testPoi() {
        try {
            List<Sample> sampleList = PoiUtil.resolveExcel(new FileInputStream(fileName));
            System.out.println(sampleList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRedis() {
        Boolean isSuccess = redisTemplate.opsForValue().setIfAbsent("limit", "127.0.0.1", 10, TimeUnit.SECONDS);
        System.out.println(isSuccess);
    }

    @Test
    void testFileCopy() throws IOException {
        InputStream is = StockApplicationTest.class.getClassLoader().getResourceAsStream("com/lj/stock/files/1.jpg");
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] bytes = bis.readAllBytes();
        String path = "src/main/java/com/lj/stock/files/2.jpg";
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
        if(!new File(path).exists()){
            bos.write(bytes);
            System.out.println("创建了新文件...");
        }
        bos.close();
        bis.close();
    }
}

