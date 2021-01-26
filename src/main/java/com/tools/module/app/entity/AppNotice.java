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
@Table(name = "app_notice")
public class AppNotice extends PageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    /**
     * 通道
     */
    @Column(name = "channel", nullable = false, length = 200)
    private String channel;

    /**
     * 发送内容
     */
    @Column(name = "content", nullable = false, length = 65535)
    private String content;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp gmtCreate;

    /**
     * 创建人ID
     */
    @Column(name="user_create", length = 20)
    private Long userCreate;

    /**
     * 创建人(昵称)
     */
    @Column(name = "nickname", length = 50)
    private String nickname;

}