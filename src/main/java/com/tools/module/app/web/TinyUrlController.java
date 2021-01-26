package com.tools.module.app.web;

import com.tools.common.constant.SystemConstant;
import com.tools.common.model.Result;
import com.tools.common.util.HttpClient;
import com.tools.module.app.entity.AppTinyUrl;
import com.tools.module.app.service.AppTinyUrlService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短连接
 * @author 小柒2012
 */
@Api(tags ="短连管理")
@RestController
@RequestMapping("app/tinyUrl")
public class TinyUrlController {

    @Autowired
    private AppTinyUrlService tinyUrlService;

    /**
     * 生成
     * @param tinyUrl
     * @return
     */
    @RequestMapping("save")
    public Result save(AppTinyUrl tinyUrl) {
        Integer code = HttpClient.client(tinyUrl.getUrl());
        if(SystemConstant.CODE_200.equals(code.toString())){
            return tinyUrlService.save(tinyUrl);
        }else{
            return Result.error("是不是网址心里没点数吗？瞎JB输！");
        }
    }
}
