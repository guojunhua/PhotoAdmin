package com.tools.common.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import lombok.Data;

import java.io.Serializable;

/**
 * 简单的用户封装
 * @author 小柒2012
 */
@Data
@ColumnWidth(22)
@ContentRowHeight(15)
public class User implements Serializable {
    /**
     * value: 表头名称
     * index: 列的号, 0表示第一列
     *
     */
    /**
     * 入库时间
     */

    @ExcelProperty(value = "姓名", index = 0)
    private String name;
    /**
     * 供应商
     */
    @ExcelProperty(value = "年龄", index = 1)
    private String age;
    /**
     * 纸筒编号
     */
    @ExcelProperty(value = "性别", index = 2)
    private String sex;

}
