package com.tools.module.wechat.web;

import com.tools.common.model.Result;
import com.tools.module.wechat.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 今日推荐
 * @author 小柒2012
 */
@RestController
@RequestMapping("sys/push")
public class PushController {

    @Autowired
    private PushService pushService;
    /**
     * 推送消息
     * @param id
     * @return
     */
    @RequestMapping("/today")
    public Result today(Long id) {
        return pushService.today(id);
    }
}
