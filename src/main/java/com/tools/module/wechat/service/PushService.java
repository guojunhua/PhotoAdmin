package com.tools.module.wechat.service;


import com.tools.common.model.Result;

/**
 * 订阅推送
 */
public interface PushService {
    Result push();

    Result today(Long id);
}

