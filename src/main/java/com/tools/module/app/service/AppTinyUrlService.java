package com.tools.module.app.service;

import cn.hutool.db.Page;
import com.tools.common.model.Result;
import com.tools.module.app.entity.AppTinyUrl;

/**
 * @Description 短链管理
 * @Author 小柒2012
 * @Date 2022/09/09
 */
public interface AppTinyUrlService {

    /**
     * 保存
     * @param tinyUrl
     * @return
     */
    Result save(AppTinyUrl tinyUrl);

    /**
     * 获取原始链接
     * @param tinyKey
     * @return
     */
    String getUrl(String tinyKey);

    /**
     * 列表
     * @param page
     * @return
     */
    Result list(Page page);
}

