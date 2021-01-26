package com.tools.module.app.service;


import com.tools.common.model.Result;
import com.tools.module.app.entity.AppTask;
import org.quartz.SchedulerException;

/**
 * @Description 任务管理
 * @Author 小柒2012
 * @Date 2022/09/09
 */
public interface AppTaskService {

    /**
     * 列表
     * @param task
     * @return
     */
    Result listQuartzEntity(AppTask task);

    /**
     * 保存
     * @param task
     * @throws Exception
     */
    void save(AppTask task) throws Exception;

    /**
     * 删除
     * @param task
     * @throws SchedulerException
     */
    void delete(AppTask task) throws SchedulerException;

    /**
     * 启用、恢复
     * @param task
     * @throws SchedulerException
     */
    void resume(AppTask task) throws SchedulerException;

    /**
     * 数量
     * @return
     */
    long count();
}
