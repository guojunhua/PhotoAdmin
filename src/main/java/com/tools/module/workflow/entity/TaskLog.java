package com.tools.module.workflow.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 工作流操作日志
 * @Author 小柒2012
 */
@Data
@Entity
@Table(name = "bpm_task_log")
public class TaskLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "process_instance_id")
	private String processInstanceId;

	@Column(name = "task_id")
	private String taskId;

	@Column(name = "user_create")
	private long userCreate;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "gmt_create")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp gmtCreate;

	@Column(name = "task_name")
	private String taskName;

	@Column(name = "approve")
	private String approve;

	@Length(max=200,message="您输入的备注信息过长，请修改")
	@Column(name = "comments")
	private String comments;

	@Column(name = "start_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp startTime;
}