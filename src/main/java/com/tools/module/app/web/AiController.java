package com.tools.module.app.web;

import com.tools.common.model.Result;
import com.tools.module.app.util.BaiDuUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 人工智能
 * @author 小柒2012
 */
@Api(tags = "人工智能")
@RestController
@RequestMapping("app/ai")
public class AiController {

    @Autowired
    private BaiDuUtils baiDuUtils;

    /**
     * 文字转语音
     */
    @PostMapping("/text2Voice")
    public Result text2Voice(String text, Boolean per) {
        String voice = baiDuUtils.text2Voice(text, per);
        return Result.ok("/file/" + voice);
    }
}
