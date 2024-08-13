package com.lj.stock.utils;

import com.lj.stock.model.Sample;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PoiUtil {
    /**
     * 解析excel中的数据并封装
     * @param inputStream
     * @return
     */
    @SneakyThrows
    public static List<Sample> resolveExcel(InputStream inputStream){
        List<Sample> list = new ArrayList<>();
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = workbook.getSheetAt(0);
        Field[] fields = Sample.class.getDeclaredFields();
        //获取sheet的每一行,将每一行中每一个cell中的值赋值给sample对应的属性
        int totalRows = 0;
        for (int i = 3; i < sheet.getPhysicalNumberOfRows(); i++) {
            Sample sample = new Sample();
            for (int j = 0; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++) {
                HSSFCell cell = sheet.getRow(i).getCell(j);
                //判断cell是否为空单元格
                if(cell.getCellType() != CellType.BLANK){
                    Field field = fields[j];
                    Class type = field.getType();
                    field.setAccessible(true);
                    field.set(sample,getCellValue(cell,type));
                }
            }
            //样品的id不为空时才能添加
            if (Objects.nonNull(sample.getSampleId()) && !sample.getSampleId().isBlank()){
                list.add(sample);
            }
        }
        System.out.println("长度为:" + list.size());
        return list;
    }

    private static Object getCellValue(Cell cell,Class type){
        switch (cell.getCellType()){
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case NUMERIC:
                if (type == Integer.class) {
                    return (int) cell.getNumericCellValue();
                }else if(type == Long.class){
                    return (long) cell.getNumericCellValue();
                }else if(type == Double.class){
                    return cell.getNumericCellValue();
                }
            default:
                return null;
        }
    }
}
