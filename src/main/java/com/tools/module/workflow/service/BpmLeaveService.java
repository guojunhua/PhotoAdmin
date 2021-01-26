package com.tools.module.workflow.service;

import com.tools.common.model.Result;
import com.tools.module.workflow.entity.BpmEntity;
import com.tools.module.workflow.entity.BpmLeave;

/**
 * @Description 请假流程
 * @Author 小柒2012
 * @Date 2022/09/09
 */
public interface BpmLeaveService {

    /**
     * 获取
     * @param id
     * @return
     */
    Result get(Long id);

    /**
     * 保存
     * @param leave
     * @return
     */
    Result save(BpmLeave leave);

    /**
     * 审核
     * @param entity
     * @return
     */
    Result audit(BpmEntity entity);

    /**
     * 我发起的休假
     * @param leave
     * @return
     */
    Result myList(BpmLeave leave);

    /**
     * 休假待办任务
     * @param leave
     * @return
     */
    Result toDoList(BpmLeave leave);

    /**
     * 休假已办任务
     * @param leave
     * @return
     */
    Result doneList(BpmLeave leave);

}