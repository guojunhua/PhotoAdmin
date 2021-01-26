package com.tools.module.app.service;


import com.tools.common.model.Result;
import com.tools.module.app.model.AppGen;

import java.util.List;

/**
 * @Description 敏捷开发
 * @Author 小柒2012
 * @Date 2022/09/09
 */
public interface AppGenService {

    /**
     * 获取表列表
     * @param gen
     * @return
     */
    Result list(AppGen gen);

    /**
     * 获取单个表信息
     * @param gen
     * @return
     */
    List<AppGen> getByTable(AppGen gen);
}
