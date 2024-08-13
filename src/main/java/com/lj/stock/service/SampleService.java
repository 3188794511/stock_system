package com.lj.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lj.stock.model.Sample;

import java.io.InputStream;
import java.util.List;

public interface SampleService extends IService<Sample> {

    Boolean loadExcelData(InputStream inputStream);

    List<Sample> pageQuery(Integer page, Integer pageSize, Sample sample);
}
