package com.tools.module.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.tools.common.constant.SystemConstant;
import com.tools.common.model.Result;
import com.tools.common.util.DateUtils;
import com.tools.common.util.HttpUtils;
import com.tools.module.app.entity.AppDingSignInLog;
import com.tools.module.app.entity.AppDingUser;
import com.tools.module.app.service.AppDingLogService;
import com.tools.module.app.service.AppDingService;
import com.tools.module.app.service.AppDingUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 蘑菇钉
 * @Author 爪洼笔记
 * @Date 2022/9/34
 */
@Service("appDingService")
public class AppDingServiceImpl implements AppDingService {


    @Override
    public String login(AppDingUser user) {
        /* 请求头 */
        Map<String,String> headersMap = new HashMap(20);
        headersMap.put("Accept-Language", "zh-CN,zh;q=0.8");
        headersMap.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 8.0.0; zh-cn; MI 6 Build/OPR1.170623.027) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        headersMap.put("Authorization", "");
        headersMap.put("roleKey", "");
        headersMap.put("Content-Type", "application/json; charset=UTF-8");
        headersMap.put("Content-Length", "85");
        headersMap.put("Host", "api.moguding.net:9000");
        headersMap.put("Connection", "close");
        headersMap.put("Accept-Encoding", "gzip, deflate");
        headersMap.put("Cache-Control", "no-cache");
        /* 请求地址 */
        String url = "https://api.moguding.net:9000/session/user/v1/login";
        /* 请求参数 */
        Map<String,String> paramsMap = new HashMap(10);
        paramsMap.put("phone", user.getUsername());
        paramsMap.put("password", user.getPassword());
        paramsMap.put("loginType", "android");
        paramsMap.put("uuid", "");
        /* 请求参数转为json字符串 */
        String params = JSON.toJSONString(paramsMap);
        try {
            /**
             * 发送请求
             */
            String post = HttpUtils.post(url, params, headersMap);
            String code = JSON.parseObject(post).getString("code");
            logger.info("{}登录{}",user.getUsername(),post);
            if(SystemConstant.CODE_500.equals(code)){
                String msg = JSON.parseObject(post).getString("msg");
                logger.error("{}登录失败：{}",user.getUsername(),msg);
                return null;
            }else{
                /**
                 * 获取响应参数
                 */
                String Authorization = JSON.parseObject(post).getString("data");
                Authorization = JSON.parseObject(Authorization).getString("token");
                return Authorization;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String planId(String authorization) {
        /* 请求头 */
        Map<String,String> headersMap = new HashMap(20);
        headersMap.put("Accept-Language", "zh-CN,zh;q=0.8");
        headersMap.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 8.0.0; zh-cn; MI 6 Build/OPR1.170623.027) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        headersMap.put("Authorization", authorization);
        headersMap.put("roleKey", "student");
        headersMap.put("Content-Type", "application/json; charset=UTF-8");
        headersMap.put("Content-Length", "500");
        headersMap.put("Host", "api.moguding.net:9000");
        headersMap.put("Connection", "close");
        headersMap.put("Accept-Encoding", "gzip, deflate");
        headersMap.put("Cache-Control", "no-cache");
        /* 请求地址 */
        String url = "https://api.moguding.net:9000/practice/plan/v1/getPlanByStu";
        /* 请求参数 */
        Map<String,String> paramsMap = new HashMap(10);
        paramsMap.put("state", "");
        /* 请求参数转为json字符串 */
        String params = JSON.toJSONString(paramsMap);
        try {
            /**
             * 发送请求
             */
            String post = HttpUtils.post(url, params, headersMap);
            String code = JSON.parseObject(post).getString("code");
            logger.info("获取planId{}",post);
            if(SystemConstant.CODE_500.equals(code)){
                String msg = JSON.parseObject(post).getString("msg");
                logger.error("获取planId失败{}",msg);
                return null;
            }else{
                /**
                 * 获取响应参数
                 */
                String planId = JSON.parseObject(post).getString("data");
                JSONArray array = JSON.parseArray(planId);
                planId = array.get(0).toString();
                planId = JSON.parseObject(planId).getString("planId");
                return planId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String sin(String authorization, String planId, AppDingUser detail) {
        /* 请求头 */
        Map<String,String> headersMap = new HashMap(20);
        headersMap.put("Accept-Language", "zh-CN,zh;q=0.8");
        headersMap.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 8.0.0; zh-cn; MI 6 Build/OPR1.170623.027) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        headersMap.put("Authorization", authorization);
        headersMap.put("roleKey", "student");
        headersMap.put("Content-Type", "application/json; charset=UTF-8");
        headersMap.put("Content-Length", "500");
        headersMap.put("Host", "api.moguding.net:9000");
        headersMap.put("Connection", "close");
        headersMap.put("Accept-Encoding", "gzip, deflate");
        headersMap.put("Cache-Control", "no-cache");

        /* 请求地址 */
        String url = "https://api.moguding.net:9000/attendence/clock/v1/save";

        /* 请求参数 */
        Map<String,String> paramsMap = new HashMap(20);
        paramsMap.put("country", detail.getCountry());
        paramsMap.put("address", detail.getAddress());
        paramsMap.put("province", detail.getProvince());
        paramsMap.put("city", detail.getCity());
        paramsMap.put("latitude", detail.getLatitude());
        paramsMap.put("description", detail.getDescription());
        paramsMap.put("planId", planId);
        paramsMap.put("type", detail.getType());
        paramsMap.put("device", "Android");
        paramsMap.put("longitude", detail.getLongitude());
        /* 请求参数转为json字符串 */
        String params = JSON.toJSONString(paramsMap);
        try {
            String post = HttpUtils.post(url, params, headersMap);
            String code = JSON.parseObject(post).getString("code");
            logger.info("{}签到{}",detail.getUsername(),post);
            if(SystemConstant.CODE_500.equals(code)){
                String msg = JSON.parseObject(post).getString("msg");
                logger.error("签到失败{}",msg);
                return null;
            }else{
                return post;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Autowired
    private AppDingUserService dingUserService;
    @Autowired
    private AppDingLogService dingLogService;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Result sign(AppDingUser user) {
        AppDingSignInLog log = new AppDingSignInLog();
        log.setUsername(user.getUsername());
        log.setAddress(user.getAddress());
        log.setGmtCreate(DateUtils.getTimestamp());
        /**
         * 获取 authorization
         */
        String authorization = login(user);
        if(StringUtils.isNotBlank(authorization)){
            /**
             * 获取 planId
             */
            String planId = planId(authorization);
            if(StringUtils.isNotBlank(planId)){
                String sin = sin(authorization, planId, user);
                if(StringUtils.isNotBlank(sin)){
                    log.setStatus(SystemConstant.SIGN_STATUS_YES);
                    logger.info("{}签到成功",user.getUsername());
                }else{
                    log.setStatus(SystemConstant.SIGN_STATUS_NO);
                    logger.error("{}签到失败",user.getUsername());
                    log.setMessage("签到失败");
                    return Result.error("签到失败");
                }
            }else{
                log.setStatus(SystemConstant.SIGN_STATUS_NO);
                logger.error("{}获取planId失败",user.getUsername());
                log.setMessage("获取planId失败");
                return Result.error("获取planId失败");
            }
        }else{
            log.setStatus(SystemConstant.SIGN_STATUS_NO);
            logger.error("{}获取authorization失败",user.getUsername());
            log.setMessage("获取authorization失败");
            return Result.error("认证失败");
        }
        /**
         * 保存日志
         */
        dingLogService.save(log);
        return Result.ok();
    }
}
