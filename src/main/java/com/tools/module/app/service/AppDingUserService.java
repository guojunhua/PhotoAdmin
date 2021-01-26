package com.tools.module.app.service;

import com.tools.common.model.Result;
import com.tools.module.app.entity.AppDingUser;

import java.util.List;

/**
 * @Description 蘑菇钉用户
 * @Author 小柒2012
 * @Date 2022/09/09
 */
public interface AppDingUserService {

    /**
     * 列表
     * @param user
     * @return
     */
    Result list(AppDingUser user);

    /**
     * 所有用户
     * @return
     */
    List<AppDingUser> listUser();

    /**
     * 获取单个用户
     * @param userId
     * @return
     */
    AppDingUser get(Integer userId);

    /**
     * 保存
     * @param user
     * @return
     */
    Result save(AppDingUser user);

    /**
     * 删除
     * @param userId
     * @return
     */
    Result delete(Integer userId);

    /**
     * 更新
     * @param user
     * @return
     */
    Result update(AppDingUser user);
}
