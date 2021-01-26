package com.tools.module.wechat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *  今日推送详情
 */
@Data
@Entity
@Table(name = "app_push")
public class Push implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "recommend_id")
    private Long recommendId;

    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name="gmt_create")
    private Timestamp gmtCreate;

    @Column(name = "count")
    private Long count;//今日推送次数
}
