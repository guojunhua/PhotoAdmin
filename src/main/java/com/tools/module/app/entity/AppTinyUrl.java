package com.tools.module.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * app_short_url 实体类
 * Created by 小柒2012
 * Wed Nov 13 15:32:08 CST 2019
 */
@Data
@Entity
@Table(name = "app_tiny_url")
public class AppTinyUrl implements Serializable{
   /**
    * 主键 
    */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 20)
	private Long id;

   /**
    * 类型：0 转发  1 重定向
    */
	@Column(name = "type", nullable = false, length = 4)
	private Short type;

   /**
    * 短连接key
    */
	@Column(name = "tiny_key", nullable = false, length = 100)
	private String tinyKey;

	/**
	 * hash_code
	 */
	@Column(name = "hash_key", nullable = false, length = 11)
	private Integer hashKey;

   /**
    * 原地址
    */
	@Column(name = "url", nullable = false, length = 200)
	private String url;

	/**
	 * 浏览量
	 */
	@Column(name = "view", nullable = false, length = 20)
	private Long view;

	/**
	 * 删除：0 删除  1 正常
	 */
	@Column(name = "del_status", nullable = false, length = 4)
	private Short delStatus;

	/**
	 * 过期时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "gmt_expire")
	private Timestamp gmtExpire;
	/**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "gmt_create")
	private Timestamp gmtCreate;
	/**
	 * 创建人ID
	 */
	@Column(name="user_create", length = 20)
	private Long userCreate;
	/**
	 * 创建人昵称
	 */
	@Column(name="nickname")
	private String nickname;
}

