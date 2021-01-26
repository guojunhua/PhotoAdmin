package com.tools.module.app.service;


import com.tools.common.model.Result;

/**
 * @Description 在线查询
 * @Author 小柒2012
 * @Date 2022/09/09
 */
public interface AppQueryService {

    /**
     * 自由查询
     * @param sql
     * @return
     */
    Result crud(String sql);
}
