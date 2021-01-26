package com.tools.common.config;

import com.tools.module.app.service.AppTinyUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 短连接跳转
 * @author 小柒2012
 */
@Controller
@RequestMapping
public class SkipController {

    @Autowired
    private AppTinyUrlService tinyUrlService;

    @GetMapping("/r/{tinyKey}")
    public ModelAndView  redirect(@PathVariable("tinyKey") String tinyKey) {
        ModelAndView mv = new ModelAndView();
        String url = tinyUrlService.getUrl(tinyKey);
        mv.setViewName("redirect:"+url);
        return mv;
    }
}
