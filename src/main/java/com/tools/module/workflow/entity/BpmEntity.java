package com.tools.module.workflow.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 工作流通用传输对象
 * @author 小柒2012
 */
@Data
public class BpmEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 业务ID
	 */
	private Integer businessId;

	/**
	 * 任务ID
	 */
	private String taskId;
	/**
	 * 流程ID
	 */
	private String processInstanceId;
	/**
	 * 评论意见
	 */
	private String comment;
	/**
	 * 指派用户ID
	 */
	private String assignUserId;
	/**
	 * 指派角色
	 */
	private String assignRole;
	/**
	 * 审批类型 通过、拒绝等等
	 */
	private String approve;

	/**
	 * 通用字段
	 */
	private String remark;

}
