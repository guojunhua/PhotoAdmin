package com.tools.module.app.util;

import java.io.File;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

/**
 * 阿里云存储
 * @author 小柒2012
 */
@Component
@Configuration
@EnableConfigurationProperties({AliYunProperties.class})
public class AliYunUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(AliYunUtils.class);

    private AliYunProperties aliYun;

    public AliYunUtils(AliYunProperties aliYun) {
        this.aliYun = aliYun;
    }

    private OSS instance;

    @PostConstruct
    public void init() {
        try {
            instance = new OSSClientBuilder().build(aliYun.getEndpoint(), aliYun.getAccessKeyId(),
                    aliYun.getAccessKeySecret());
        } catch (Exception e) {
            LOGGER.error("阿里云OSS初始化失败，请配置正确的参数");
        }
    }

    /**
     * 上传
     */
    public void upload(File file, String fileName) {
        instance.putObject(aliYun.getBucketName(), fileName, file);
    }

    /**
     * 上传
     */
    public void upload(InputStream file, String fileName) {
        instance.putObject(aliYun.getBucketName(), fileName, file);
    }
}