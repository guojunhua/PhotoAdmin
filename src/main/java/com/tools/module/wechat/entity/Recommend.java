package com.tools.module.wechat.entity;

import cn.hutool.db.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 每日推荐
 */
@Data
@Entity
@Table(name = "app_recommend")
public class Recommend extends Page implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name="uuid")
    private String uuid;

    @Column(name="title")
    private String title;

    @Column(name="type")
    private String type;

    @Column(name="first_picture")
    private String firstPicture;

    @Lob
    @Column(name="content",columnDefinition="text")
    private String content;

    @Column(name="user_create")
    private Integer userCreate;

    @Column(name="username")
    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name="gmt_create")
    private Timestamp gmtCreate;

    @Column(name="category")
    private String category;

    @Column(name="status")
    private Short status;

    @Column(name = "view")
    private Long view;

    @Column(name="hot")
    private Short hot;//今日推荐

}
