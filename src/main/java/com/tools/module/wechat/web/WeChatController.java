package com.tools.module.wechat.web;

import com.tools.common.model.Result;
import com.tools.module.wechat.entity.WeChat;
import com.tools.module.wechat.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 微信小程序
 * @author 小柒2012
 */
@RestController
@RequestMapping("sys/weChat")
public class WeChatController {

    @Autowired
    private WeChatService weChatService;

    /**
     * 用户列表
     */
    @PostMapping("/list")
    public Result list(WeChat weChat){
        return weChatService.list(weChat);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long id){
        return weChatService.delete(id);
    }

}
