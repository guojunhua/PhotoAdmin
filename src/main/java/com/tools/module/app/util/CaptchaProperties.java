package com.tools.module.app.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云存储
 * @author 小柒2012
 */
@Data
@ConfigurationProperties(prefix = "qq.captcha")
public class CaptchaProperties {

    private String url;
    private String aid;
    private String AppSecretKey;
    private Boolean open;

}
