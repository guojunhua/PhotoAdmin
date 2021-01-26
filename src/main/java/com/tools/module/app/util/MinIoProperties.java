package com.tools.module.app.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "min.io")
public class MinIoProperties {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;

}
