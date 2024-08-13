package com.lj.stock.controller;

import com.lj.stock.annotation.Limit;
import com.lj.stock.model.Result;
import com.lj.stock.model.Sample;
import com.lj.stock.service.SampleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/stock-system/sample")
class SampleController {
    @Resource
    private SampleService sampleService;

    @GetMapping("/test")
    public Result test(){
        return Result.ok().message("hello world!");
    }

    @GetMapping("/selectAll")
    public List<Sample> selectAll(){
        return sampleService.list();
    }

    @PostMapping("/import")
    public Result importBatch(@RequestParam("file") MultipartFile file) throws IOException {
        Boolean isSuccess = sampleService.loadExcelData(file.getInputStream());
        return isSuccess ? Result.ok().message("数据导入成功") : Result.fail().message("数据导入失败");
    }

    @GetMapping("/pageQuery/{page}/{pageSize}")
    @Limit(limitTime = 15)
    public Result<List<Sample>> pageQuery(@PathVariable("page") Integer page,@PathVariable("pageSize") Integer pageSize, Sample sample){
        List<Sample> sampleList = sampleService.pageQuery(page,pageSize,sample);
        return Result.ok(sampleList);
    }
}
