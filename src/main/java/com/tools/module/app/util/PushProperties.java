package com.tools.module.app.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "push")
public class PushProperties {

    /**
     * 区域
     */
    private String url;
    /**
     * 既可以发送消息或也可以订阅channel来接收消息
     */
    private String commonKey;
    /**
     * 只能用来订阅channel来接收消息
     */
    private String subscribeKey;

}
