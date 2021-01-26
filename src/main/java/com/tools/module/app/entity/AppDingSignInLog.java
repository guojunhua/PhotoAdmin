package com.tools.module.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tools.common.model.PageBean;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "app_ding_signIn_log")
public class AppDingSignInLog  extends PageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer logId;

    @Column(name = "username")
    private String username;

    @Column(name = "address")
    private String address;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp gmtCreate;

    @Column(name = "message")
    private String message;

    /**
     * 发送 状态 0 失败 1正常
     */
    @Column(name = "status")
    private short status;
}
