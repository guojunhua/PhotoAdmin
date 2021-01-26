package com.tools.module.app.model;

import com.tools.common.model.PageBean;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 表以及相关字段信息
 * @author 小柒2012
 */
@Data
public class AppGen extends PageBean implements Serializable {

    /**
     * 表名
     */
    private String tableName;
    /**
     * 实体类名
     */
    private String entityName;
    /**
     * 实体类名 首字母小写
     */
    private String lowerEntityName;
    /**
     * 表备注
     */
    private String tableComment;
    /**
     * 表前缀
     */
    private String prefix;
    /**
     * 功能描述
     */
    private String function;

    /**
     * 列名
     */
    private String columnName;
    /**
     * 实体列名
     */
    private String entityColumnName;
    /**
     * 列描述
     */
    private String columnComment;

    /**
     * 类型
     */
    private String dataType;

    /**
     * 自增
     */
    private Object columnExtra;
    /**
     * 长度
     */
    private Object columnLength;

    private List<AppGen> list;

}
