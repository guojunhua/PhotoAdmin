package com.tools.module.wechat.entity;

import cn.hutool.db.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 妹子图
 */
@Data
@Entity
@Table(name = "app_girl")
public class Girl extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name="uuid")
    private String uuid;
    @Column(name="url")
    private String url;
    @Column(name="oss_url")
    private String ossUrl;
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name="gmt_create")
    private Timestamp gmtCreate;
    @Column(name="status")
    private Short status;
}
