package com.tools.module.app.util;

import com.alibaba.fastjson.JSONObject;
import com.tools.common.util.HttpUtils;
import com.tools.common.util.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 智能的人机安全验证
 * @author 小柒2012
 */
@Component
@Configuration
@EnableConfigurationProperties({CaptchaProperties.class})
public class CaptchaUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(CaptchaUtils.class);

    private CaptchaProperties captcha;

    public CaptchaUtils(CaptchaProperties captcha) {
        this.captcha = captcha;
    }


    public Boolean check(String ticket,String randstr) throws Exception {
        if(captcha.getOpen()){
            Map<String, String> paramsMap= new HashMap<>();
            paramsMap.put("aid", captcha.getAid());
            paramsMap.put("AppSecretKey", captcha.getAppSecretKey());
            paramsMap.put("Ticket", ticket);
            paramsMap.put("Randstr", randstr);
            paramsMap.put("UserIP", IPUtils.getIpAddr());
            String msg = HttpUtils.get(captcha.getUrl(), paramsMap);
            /**
             * response: 1:验证成功，0:验证失败，100:AppSecretKey参数校验错误[required]
             * evil_level:[0,100]，恶意等级[optional]
             * err_msg:验证错误信息[optional]
             */
            System.out.println(msg);
            JSONObject json = JSONObject.parseObject(msg);
            String response = (String) json.get("response");
            if("1".equals(response)){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }
}