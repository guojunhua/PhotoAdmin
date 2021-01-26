package com.tools.module.app.service;

import com.tools.common.model.Result;
import com.tools.module.app.entity.AppImage;

/**
 * @Description 图片管理
 * @Author 小柒2012
 * @Date 2022/09/09
 */
public interface AppImageService {

    /**
     * 上传
     * @param image
     */
    void upload(AppImage image) ;

    /**
     * 列表
     * @param image
     * @return
     */
    Result list(AppImage image);
}

