package com.tools.module.app.service;

import com.tools.common.model.Result;
import com.tools.module.app.entity.AppDingSignInLog;

/**
 * @Description 蘑菇钉日志
 * @Author 小柒2012
 * @Date 2022/09/09
 */
public interface AppDingLogService {

    /**
     * 打卡日志
     * @param log
     * @return
     */
    Result listSignInLog(AppDingSignInLog log);

    /**
     * 删除日志
     * @param logId
     * @return
     */
    Result delete(Integer logId);

    /**
     * 保存日志
     * @param signInLog
     */
    void save(AppDingSignInLog signInLog);

}
