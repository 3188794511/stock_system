package com.lj.stock.model;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_sample")
public class Sample {
    @ExcelProperty(value = "序号",index = 0)
    private Long orderId;
    @TableId(type = IdType.INPUT)
    @ExcelProperty(value = "样品编号",index = 1)
    private String sampleId;
    @ExcelProperty(value = "调查船",index = 2)
    private String shipName;
    @ExcelProperty(value = "航次",index = 3)
    private String flightId;

    @ExcelProperty(value = " 海域",index = 4)
    private String seaArea;
    @ExcelProperty(value = "站位",index = 5)
    private String location;
    @ExcelProperty(value = "X坐标",index = 6)
    private Double xPoint;
    @ExcelProperty(value = "Y坐标",index = 7)
    private Double yPoint;
    @ExcelProperty(value = "详细地址",index = 8)
    private String detailAddress;

    @ExcelProperty(value = "结束深度（m）",index = 9)
    private Double endDeep;
    @ExcelProperty(value = "心长（m）",index = 10)
    private Double heartLength;
    @ExcelProperty(value = "存放位置",index = 11)
    private String savePosition;
    @ExcelProperty(value = "保存状况",index = 12)
    private Double saveStatus;
    @ExcelProperty(value = "备注",index = 13)
    private String remarks;


}
