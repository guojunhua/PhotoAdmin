package com.tools.module.app.web;

import com.tools.common.config.AbstractController;
import com.tools.common.model.Result;
import com.tools.module.app.entity.AppTask;
import com.tools.module.app.service.AppTaskService;
import io.swagger.annotations.Api;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务调度
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012
 */
@Api(tags ="任务调度")
@RestController
@RequestMapping("/app/task")
public class TaskController extends AbstractController {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private AppTaskService taskService;

    /**
     * 新增
     * @param task
     * @return
     */
    @PostMapping("/save")
    public Result save(AppTask task) throws Exception {
        taskService.save(task);
        return Result.ok();
    }

    /**
     * 任务列表
     * @param task
     * @return
     */
    @PostMapping("/list")
    public Result list(AppTask task) throws SchedulerException {
        return taskService.listQuartzEntity(task);
    }

    @PostMapping("/trigger")
    public  Result trigger(AppTask task) throws SchedulerException {
        JobKey key = new JobKey(task.getName(),task.getGroup());
        scheduler.triggerJob(key);
        return Result.ok();
    }

    /**
     * 执行/停止
     * @param task
     * @return
     */
    @PostMapping("/resume")
    public  Result resume(AppTask task) throws SchedulerException {
        taskService.resume(task);
        return Result.ok();
    }

    /**
     * 删除
     * @param task
     * @return
     */
    @PostMapping("/delete")
    public  Result delete(AppTask task) throws SchedulerException {
        taskService.delete(task);
        return Result.ok();
    }
}
