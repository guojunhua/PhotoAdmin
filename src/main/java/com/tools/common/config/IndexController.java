package com.tools.common.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * 通用访问拦截匹配
 * 爪哇笔记 https://blog.52itstyle.vip
 * @author 小柒2012
 */
@Controller
public class IndexController {


    @GetMapping("login.html")
    public String login() { return "login"; }

    @GetMapping("index.html")
    public String index() { return "index"; }

    @GetMapping("console.html")
    public String console() { return "console"; }

    @GetMapping("{module}/{url}.html")
    public String page(@PathVariable("module") String module,@PathVariable("url") String url) {
        return module + "/" + url;
    }
    @GetMapping("{module}/{sub}/{url}.html")
    public String page(@PathVariable("module") String module,
                       @PathVariable("url") String url,
                       @PathVariable("sub") String sub) {
        return module + "/" + sub + "/" + url;
    }
    @GetMapping("{module}/{sub}/{smallSub}/{url}.html")
    public String page(@PathVariable("module") String module,
                       @PathVariable("url") String url,
                       @PathVariable("sub") String sub,
                       @PathVariable("smallSub") String smallSub) {
        return module + "/" + sub + "/" + smallSub + "/" + url;
    }

}