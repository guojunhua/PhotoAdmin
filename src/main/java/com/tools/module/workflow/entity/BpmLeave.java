package com.tools.module.workflow.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.tools.common.model.PageBean;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * 休假
 * @author 小柒2012
 */
@Data
@Entity
@Table(name = "bpm_leave")
public class BpmLeave extends PageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    /**
     * 流程ID
     */
    @Column(name="process_instance_id")
    private String processInstanceId;
    /**
     * 休假类型
     */
    @Column(name = "type")
    private String type;
    /**
     * 开始日期
     */
    @Column(name = "begin_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String beginDate;
    /**
     * 结束日期
     */
    @Column(name = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String endDate;
    /**
     * 休假天数
     */
    @Column(name = "day")
    private String day;
    /**
     * 请假原因
     */
    @Column(name = "reason")
    private String reason;

    @Column(name = "user_create")
    private Long userCreate;

    @Column(name = "nickname")
    private String nickname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "gmt_create")
    private Timestamp gmtCreate;

    @Transient
    private String status;

    @Transient
    private String taskId;
}
