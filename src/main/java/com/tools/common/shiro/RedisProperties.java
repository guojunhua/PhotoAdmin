package com.tools.common.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * redis参数配置
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012.zhang
 */
@Data
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

    private String host;
    private int port;
    private int timeout;
    private String password;
    private int database;

}
