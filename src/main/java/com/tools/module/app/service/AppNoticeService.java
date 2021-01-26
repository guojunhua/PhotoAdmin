package com.tools.module.app.service;

import com.tools.common.model.Result;
import com.tools.module.app.entity.AppNotice;

/**
 * @Description 通知管理
 * @Author 小柒2012
 * @Date 2022/09/09
 */
public interface AppNoticeService {

    /**
     * 获取
     * @param id
     * @return
     */
    Result get(Long id);

    /**
     * 保存
     * @param notice
     * @return
     */
    Result save(AppNotice notice);

    /**
     * 删除
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 列表
     * @param notice
     * @return
     */
    Result list(AppNotice notice);
}
