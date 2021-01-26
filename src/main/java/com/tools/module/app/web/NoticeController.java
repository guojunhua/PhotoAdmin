package com.tools.module.app.web;

import com.tools.common.config.AbstractController;
import com.tools.common.model.Result;
import com.tools.common.util.ShiroUtils;
import com.tools.module.app.entity.AppNotice;
import com.tools.module.app.service.AppNoticeService;
import com.tools.module.app.util.PushUtils;
import com.tools.module.sys.entity.SysUser;
import io.swagger.annotations.Api;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息管理
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012
 */
@Api(tags ="消息管理")
@RestController
@RequestMapping("/app/notice")
public class NoticeController extends AbstractController {

    @Autowired
    private PushUtils pushUtils;
    @Autowired
    private AppNoticeService noticeService;

    /**
     * 推送
     */
    @PostMapping("/save")
    public Result save(@RequestBody AppNotice notice){
        SysUser user = ShiroUtils.getUserEntity();
        notice.setUserCreate(user.getUserId());
        notice.setNickname(user.getNickname());
        noticeService.save(notice);
        /**
         * 消息推送 请自行配置 goeasy 参数
         */
        pushUtils.send(notice);
        return Result.ok();
    }
    /**
     * 列表
     */
    @PostMapping("/list")
    public Result list(AppNotice notice){
        return noticeService.list(notice);
    }
    /**
     * 查询
     */
    @PostMapping("/get")
    public Result get(Long id){
        return noticeService.get(id);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long id){
        return noticeService.delete(id);
    }

}
