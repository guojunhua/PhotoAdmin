package com.tools.module.app.util;

import com.tools.module.app.entity.AppNotice;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 消息推送
 * @author 小柒2012
 */
@Component
@Configuration
@EnableConfigurationProperties({PushProperties.class})
public class PushUtils {

    private PushProperties push;

    public PushUtils(PushProperties push) {
        this.push = push;
    }

    /**
     * 推送消息 需要配置 push 参数
     * @param notice
     * @return
     */
    public String send(AppNotice notice){
        try{
            RestTemplate client = new RestTemplate();
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("content", notice.getContent());
            paramMap.add("channel", notice.getChannel()==null?"SPTools":notice.getChannel());
            paramMap.add("appkey", push.getCommonKey());
            return client.postForObject(push.getUrl(), paramMap , String.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
