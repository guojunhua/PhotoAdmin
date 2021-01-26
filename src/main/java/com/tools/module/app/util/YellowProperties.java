package com.tools.module.app.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ucloud")
public class YellowProperties {

    private String publicKey;
    private String privateKey;
    private String resourceId;
    private String url;

}
