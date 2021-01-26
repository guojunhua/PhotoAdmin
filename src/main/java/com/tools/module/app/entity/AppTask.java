package com.tools.module.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tools.common.model.PageBean;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "app_task")
public class AppTask extends PageBean implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 20)
    private Long Id;

    /**
     * 任务名称
     */
    @Column(name = "task_name", length = 100)
    private String name;
    /**
     * 任务分组
     */
    @Column(name = "task_group", length = 100)
    private String group;
    /**
     * 任务描述
     */
    @Column(name = "description", length = 200)
    private String description;
    /**
     * 执行类
     */
    @Column(name = "task_class_name", length = 200)
    private String taskClassName;
    /**
     * 执行方法
     */
    @Column(name = "method_name", length = 100)
    private String methodName;
    /**
     * 执行时间
     */
    @Column(name = "cron_expression", length = 100)
    private String cronExpression;

    /**
     * 任务状态
     */
    @Column(name = "trigger_state", length = 100)
    private String triggerState;

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

    /**
     * 用于修改
     */
    @Transient
    private String oldName;
    @Transient
    private String oldGroup;
}
