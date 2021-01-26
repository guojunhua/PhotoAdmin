package com.tools.common.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 参数配置
 * @author 小柒2012
 */
@Data
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonProperties {

    private int timeout = 3000;

    private String host;

    private String port;

    private String password;

    private int connectionPoolSize = 64;
    
    private int connectionMinimumIdleSize=10;

    private int slaveConnectionPoolSize = 250;

    private int masterConnectionPoolSize = 250;

    private String[] sentinelAddresses;

    private String masterName;

}
