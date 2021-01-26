package com.tools.module.app.web;

import com.tools.common.config.AbstractController;
import com.tools.common.model.Result;
import com.tools.module.app.entity.AppEmail;
import com.tools.module.app.service.AppEmailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邮件管理
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012
 */
@Api(tags ="邮件管理")
@RestController
@RequestMapping("/app/email")
public class EmailController extends AbstractController {

    @Autowired
    private AppEmailService emailService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public Result list(AppEmail email){
        return emailService.list(email);
    }
    /**
     * 查询
     */
    @PostMapping("/get")
    public Result get(Long id){
        return emailService.get(id);
    }
    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody AppEmail email){
        return emailService.save(email);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long id){
        return emailService.delete(id);
    }
}
