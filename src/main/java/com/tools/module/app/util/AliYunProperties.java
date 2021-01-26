package com.tools.module.app.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云存储
 * @author 小柒2012
 */
@Data
@ConfigurationProperties(prefix = "ali-yun.oss")
public class AliYunProperties {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

}
