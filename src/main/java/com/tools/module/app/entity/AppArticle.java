package com.tools.module.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tools.common.model.PageBean;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "app_article")
public class AppArticle extends PageBean implements Serializable {
   /**
    * 自增主键
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 20)
    private Long id;
   /**
    * 分类
    */
    @Column(name = "category")
    private String category;
   /**
    * 标题
    */
    @Column(name = "title")
    private String title;
   /**
    * 内容
    */
   @Lob
   @Column(name = "content")
   private String content;
   /**
    * 首图
    */
    @Column(name = "first_picture")
    private String firstPicture;
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