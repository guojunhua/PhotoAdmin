package com.tools.module.app.service;

import com.tools.common.model.Result;
import com.tools.module.app.entity.AppEmail;
/**
 * @Description 邮件管理
 * @Author 小柒2012
 * @Date 2022/09/09
 */
public interface AppEmailService {

    /**
     * 获取
     * @param id
     * @return
     */
    Result get(Long id);

    /**
     * 保存
     * @param email
     * @return
     */
    Result save(AppEmail email);

    /**
     * 删除
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 列表
     * @param email
     * @return
     */
    Result list(AppEmail email);
}
