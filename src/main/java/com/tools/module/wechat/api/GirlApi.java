package com.tools.module.wechat.api;

import com.tools.common.model.Result;
import com.tools.module.wechat.service.GirlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图床
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012
 */
@RestController
@RequestMapping("minApp/girl")
public class GirlApi {

    @Autowired
    private GirlService meiZiService;

    /**
     * 妹子图
     * @param pageSize
     * @param pageNo
     * @return
     */
    @RequestMapping("/{pageSize}/{pageNo}")
    public Result list(@PathVariable("pageSize") Integer pageSize,
                       @PathVariable("pageNo") Integer pageNo) {
        return meiZiService.list(pageSize,pageNo);
    }

}
