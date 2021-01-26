package com.tools.module.app.service;

import com.tools.common.model.Result;
import com.tools.module.app.entity.AppDingUser;

/**
 * @Description 蘑菇钉
 * @Author 爪洼笔记
 * @Date 2022/9/34
 */
public interface AppDingService {
    /**
     * 登录获取token
     * @param user
     * @return
     */
    String login(AppDingUser user);

    /**
     * 获取计划学校ID
     * @param authorization
     * @return
     */
    String planId(String authorization);

    /**
     * 签到
     * @param authorization
     * @param planId
     * @param detail
     * @return
     */
    String sin(String authorization, String planId, AppDingUser detail);

    /**
     * 触发签到
     * @param user
     * @return
     */
    Result sign(AppDingUser user);
}
