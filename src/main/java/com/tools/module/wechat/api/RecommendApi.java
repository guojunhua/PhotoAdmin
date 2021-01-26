package com.tools.module.wechat.api;

import com.tools.common.model.Result;
import com.tools.module.wechat.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 今日推荐
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012
 */
@RestController
@RequestMapping("minApp/recommend")
public class RecommendApi {

    @Autowired
    private RecommendService recommendService;

    /**
     * 历史干货
     * @param pageSize
     * @param pageNo
     * @return
     */
    @RequestMapping("{pageSize}/{pageNo}")
    public Result history(@PathVariable("pageSize") Integer pageSize,
                          @PathVariable("pageNo") Integer pageNo) {
        return recommendService.list(pageSize,pageNo);
    }
    /**
     * 历史干货
     * @return
     */
    @RequestMapping("{uuid}")
    public Result history(@PathVariable("uuid") String uuid) {
        return recommendService.get(uuid);
    }
    /**
     * 今日推荐
     * @return
     */
    @RequestMapping("today")
    public Result today() {
        return recommendService.today();
    }

    /**
     * 更新阅读数
     * @return
     */
    @RequestMapping("view")
    public Result view(String uuid) {
        return recommendService.view(uuid);
    }
}
