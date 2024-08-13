package com.lj.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lj.stock.mapper.SampleMapper;
import com.lj.stock.model.Sample;
import com.lj.stock.service.SampleService;
import com.lj.stock.utils.PoiUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Service
public class SampleServiceImpl extends ServiceImpl<SampleMapper, Sample> implements SampleService{

    /**
     * 解析并将excel中的数据批量加载到数据库
     * @param inputStream
     * @return
     */
    @Transactional
    public Boolean loadExcelData(InputStream inputStream) {
        List<Sample> sampleList = PoiUtil.resolveExcel(inputStream);
        if(!sampleList.isEmpty()){
            boolean res = saveBatch(sampleList);
            return res;
        }
        return false;
    }

    /**
     * 分页条件查询
     * @param page
     * @param pageSize
     * @param sample
     * @return
     */
    public List<Sample> pageQuery(Integer page, Integer pageSize, Sample sample) {
        Page<Sample> samplePage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Sample> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sample.getSampleId()),Sample::getSampleId,sample.getSampleId())
                .eq(Objects.nonNull(sample.getShipName()),Sample::getShipName,sample.getShipName())
                .orderByAsc(Sample::getSampleId);
        List<Sample> records = page(samplePage, wrapper).getRecords();
        return records;
    }
}
