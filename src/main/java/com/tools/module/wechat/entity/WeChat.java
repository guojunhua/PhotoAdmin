package com.tools.module.wechat.entity;

import cn.hutool.db.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * app_weChat 实体类
 * Created by 小柒2012
 * Sun Oct 27 13:03:00 CST 2019
 */
@Data
@Entity 
@Table(name = "app_weChat")
public class WeChat extends Page implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    /**
     * 用户唯一标识
     */
    @Column(name = "openid", length = 200)
    private String openid;

   /**
    * 昵称
    */
	@Column(name = "nickName", nullable = false, length = 50)
	private String nickName;

    /**
     * 邮箱
     */
    @Column(name = "email", length = 100)
    private String email;

   /**
    * 头像地址
    */
	@Column(name = "avatarUrl", length = 2000)
	private String avatarUrl;

    /**
     * 城市
     */
    @Column(name = "city", length = 100)
    private String city;
    /**
     * 国家
     */
    @Column(name = "country", length = 100)
    private String country;

    /**
     * 性别
     */
    @Column(name = "gender", length = 4)
    private Short gender;

   /**
    * 手机号 
    */
	@Column(name = "mobile", length = 100)
	private String mobile;

   /**
    * 创建时间 
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "gmt_create")
	private Timestamp gmtCreate;

    /**
     * 订阅推送
     */
    @Column(name = "subscribe", length = 4)
    private Short subscribe;

}

