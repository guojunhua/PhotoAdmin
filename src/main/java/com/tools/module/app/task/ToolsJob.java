package com.tools.module.app.task;

import com.tools.common.util.SpringUtils;
import com.tools.module.app.entity.AppDingUser;
import com.tools.module.app.service.AppDingService;
import com.tools.module.app.service.AppDingUserService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 实现序列化接口、防止重启应用出现quartz Couldn't retrieve job because a required class was not found 的问题
 * Job 的实例要到该执行它们的时候才会实例化出来。每次 Job 被执行，一个新的 Job 实例会被创建。
 * 其中暗含的意思就是你的 Job 不必担心线程安全性，因为同一时刻仅有一个线程去执行给定 Job 类的实例，甚至是并发执行同一 Job 也是如此。
 * @author 小柒2012
 * @DisallowConcurrentExecution 保证上一个任务执行完后，再去执行下一个任务，这里的任务是同一个任务
 */
@DisallowConcurrentExecution
public class ToolsJob implements Job, Serializable {

    private static final long serialVersionUID = 1L;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AppDingService dingService;
    @Autowired
    private AppDingUserService dingUserService;

    @Override
    public void execute(JobExecutionContext context){
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap dataMap = jobDetail.getJobDataMap();
        /**
         * 获取任务中保存的方法名字，动态调用方法
         */
        String methodName = dataMap.getString("methodName");
        try {
            ToolsJob job = new ToolsJob();
            Method method = job.getClass().getMethod(methodName);
            method.invoke(job);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public void test1(){
        System.out.println("测试方法1");
    }
    public void test2(){
        System.out.println("测试方法2");
    }

    /**
     * 批量签到
     */
    public void sign(){
        dingService = SpringUtils.getBean("appDingService");
        dingUserService = SpringUtils.getBean("dingUserService");
        List<AppDingUser> list = dingUserService.listUser();
        list.forEach(user-> dingService.sign(user));
    }
}
