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
@Table(name = "app_email")
public class AppEmail extends PageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    /**
     * 接收人邮箱(多个逗号分开)
     */
    @Column(name = "receive_email", nullable = false, length = 500)
    private String receiveEmail;

    /**
     * 抄送人邮件
     */
    @Column(name = "cc_email", length = 1000)
    private String ccEmail;

    /**
     * 主题
     */
    @Column(name = "subject", nullable = false, length = 200)
    private String subject;

    /**
     * 发送内容
     */
    @Column(name = "content", nullable = false, length = 65535)
    private String content;

    /**
     * 模板
     */
    @Column(name = "template", nullable = false, length = 100)
    private String template;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp gmtCreate;

    /**
     * 发送 状态0 异常 1正常
     */
    @Column(name = "status")
    private short status;

}