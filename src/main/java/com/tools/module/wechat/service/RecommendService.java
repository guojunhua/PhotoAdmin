package com.tools.module.wechat.service;


import com.tools.common.model.Result;
import com.tools.module.wechat.entity.Recommend;

public interface RecommendService {
    /**
     * 保存
     * @param recommend
     * @return
     */
    Result save(Recommend recommend);

    /**
     * 修改
     * @param recommend
     * @return
     */
    Result update(Recommend recommend);

    /**
     * 后台列表
     * @param recommend
     * @return
     */
    Result list(Recommend recommend);

    /**
     * 小程序列表
     * @param pageSize
     * @param pageNo
     * @return
     */
    Result list(Integer pageSize, Integer pageNo);

    /**
     * 小程序获取单个
     * @param uuid
     * @return
     */
    Result get(String uuid);

    /**
     * 今日推荐
     * @return
     */
    Result today();

    /**
     * 删除
     * @param id
     * @return
     */
    Result delete(Long id);

    /**
     * 更新阅读数
     * @return
     */
    Result view(String uuid);

    /**
     * 推荐
     * @param id
     * @return
     */
    Result hot(Long id);
    /**
     * 删除全部
     * @param status
     * @return
     */
    Result removeAll(Short status);
}

