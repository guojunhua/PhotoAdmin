package com.tools.module.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tools.common.model.PageBean;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * sys_area 实体类
 * Created by 小柒2012
 * Sun Oct 27 12:56:55 CST 2019
 */
@Data
@Entity 
@Table(name = "sys_area")
public class SysArea extends PageBean implements Serializable{
   /**
    * 区域id 
    */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "area_id", nullable = false, length = 20)
	private Long areaId;

   /**
    * 行政区划代码 
    */ 
	@Column(name = "area_code", nullable = false, length = 50)
	private String areaCode;

   /**
    * 父级id 
    */ 
	@Column(name = "parent_code", nullable = false, length = 50)
	private String parentCode;

   /**
    * 地区名称 
    */ 
	@Column(name = "name", length = 20)
	private String name;

   /**
    * 层级1:省级,2:地市,3:区县 
    */ 
	@Column(name = "layer", length = 11)
	private Integer layer;

   /**
    * 排序号
    */ 
	@Column(name = "order_num", length = 11)
	private Integer orderNum;

   /**
    * 显示,1:显示,0:隐藏 
    */ 
	@Column(name = "status", length = 4)
	private Short status;

   /**
    * 备注 
    */ 
	@Column(name = "remark", length = 50)
	private String remark;

   /**
    * 创建时间 
    */ 
	@Column(name = "gmt_create")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp gmtCreate;

   /**
    * 修改时间 
    */ 
	@Column(name = "gmt_modified")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp gmtModified;
}

