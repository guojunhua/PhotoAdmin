package com.tools.module.app.task;

import com.tools.module.app.entity.AppTask;
import com.tools.module.app.service.AppTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化一个任务 测试用
 * @author 小柒2012
 */
@Component
public class TaskRunner implements ApplicationRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(TaskRunner.class);

    @Autowired
    private AppTaskService taskService;

    @Override
    public void run(ApplicationArguments var) throws Exception{
        /**
         * 系统启动的时候会初始化一个任务
         */
        Long count = taskService.count();
        if(count==0){
            LOGGER.info("初始化测试任务");
            AppTask task = new AppTask();
            task.setName("test01");
            task.setGroup("test");
            task.setDescription("测试任务");
            task.setTaskClassName("com.tools.module.app.task.ToolsJob");
            task.setCronExpression("*/5 * * * * ?");
            task.setMethodName("test1");
            taskService.save(task);
        }
    }
}