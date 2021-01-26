package com.tools.module.app.web;

import com.tools.common.config.AbstractController;
import com.tools.common.model.Result;
import com.tools.common.util.BaiDuMapUtils;
import com.tools.common.util.CommonUtils;
import com.tools.module.app.entity.AppDingSignInLog;
import com.tools.module.app.entity.AppDingUser;
import com.tools.module.app.service.AppDingLogService;
import com.tools.module.app.service.AppDingService;
import com.tools.module.app.service.AppDingUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author 爪哇笔记
 * @Date 2022/9/3
 */
@Api(tags ="蘑菇钉")
@RestController
@RequestMapping("/app/ding")
public class DingController extends AbstractController {


    @Autowired
    private AppDingService appDingService;

    @Autowired
    private AppDingUserService dingUserService;

    @Autowired
    private AppDingLogService dingLogService;

    /**
     * 列表
     */
    @PostMapping("list")
    public Result list(AppDingUser user){
        return dingUserService.list(user);
    }

    /**
     * 保存
     */
    @PostMapping("save")
    public Result save(@RequestBody AppDingUser user){
        return dingUserService.save(user);
    }

    /**
     * 获取用户
     */
    @PostMapping("get")
    public Result get(Integer userId){
        AppDingUser user = dingUserService.get(userId);
        return CommonUtils.msg(user);
    }

    /**
     * 删除用户
     */
    @PostMapping("delete")
    public Result delete(Integer userId){
        return dingUserService.delete(userId);
    }

    /**
     * 签到
     */
    @PostMapping("sign")
    public Result sign(Integer userId){
        AppDingUser user = dingUserService.get(userId);
        return appDingService.sign(user);
    }

    /**
     * 获取经纬度
     * @param address
     * @return
     */
    @PostMapping("getLocation")
    public Result getLocation(String address){
        return BaiDuMapUtils.getCoordinate(address);
    }

    /**
     * 签到日志
     */
    @PostMapping("log")
    public Result log(AppDingSignInLog log){
        return  dingLogService.listSignInLog(log);
    }

    /**
     * 删除日志
     */
    @PostMapping("log/delete")
    public Result deleteLog(Integer logId){
        return  dingLogService.delete(logId);
    }

    /**
     * 列表
     */
    @RequestMapping("signT")
    public Result sign(){
        try {
            AppDingUser user = new AppDingUser();
            user.setUsername("8888");
            user.setPassword("8888");
            String authorization = appDingService.login(user);
            System.out.println(authorization);
            String planId = appDingService.planId(authorization);
            System.out.println(planId);
            user.setAddress("河南省.郑州市.人民政府");
            user.setCountry("中国");
            user.setProvince("河南省");
            user.setCity("郑州市");
            user.setLatitude("37.773296");
            user.setLongitude("113.759527");
            user.setDescription("开心");
            String sin = appDingService.sin(authorization, planId, user);
            System.out.println(sin);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("-----------------------签到异常----------------------");
        }
        return Result.ok();
    }
}
