package com.tools.module.app.service;

import com.tools.common.model.Result;
import com.tools.module.app.entity.AppArticle;

/**
 * @Description 文章管理
 * @Author 小柒2012
 * @Date 2022/09/09
 */
public interface AppArticleService {

    /**
     * 根据ID获取文章
     * @param id
     * @return
     */
    Result get(Long id);

    /**
     * 保存
     * @param article
     * @return
     */
    Result save(AppArticle article);

    /**
     * 删除
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 列表
     * @param article
     * @return
     */
    Result list(AppArticle article);
}
