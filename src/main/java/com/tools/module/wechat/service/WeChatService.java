package com.tools.module.wechat.service;


import com.tools.common.model.Result;
import com.tools.module.wechat.entity.WeChat;

public interface WeChatService {

    Result save(WeChat weChat);

    Result subscribe(String openId);

    Result list(WeChat weChat);

    Result delete(Long id);

}

