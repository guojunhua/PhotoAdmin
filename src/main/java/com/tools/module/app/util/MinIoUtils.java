package com.tools.module.app.util;

import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * MinIo工具类
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012
 */
@Component
@Configuration
@EnableConfigurationProperties({MinIoProperties.class})
public class MinIoUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(MinIoUtils.class);

    private MinIoProperties minIo;

    public MinIoUtils(MinIoProperties minIo) {
        this.minIo = minIo;
    }

    private MinioClient instance;

    @PostConstruct
    public void init() {
        try {
            instance = new MinioClient(minIo.getEndpoint(), minIo.getAccessKey(), minIo.getSecretKey());
        } catch (Exception e) {
            LOGGER.error("MinIo初始化失败，请配置正确的参数");
        }
    }

    /**
     * 获取地址
     */
    public String getEndpoint() {
        return minIo.getEndpoint();
    }

    /**
     * 获取桶
     */
    public String getBucketName() {
        return minIo.getBucketName();
    }

    /**
     * 判断 bucket是否存在
     *
     * @param bucketName
     * @return
     */
    public boolean bucketExists(String bucketName) {
        try {
            return instance.bucketExists(bucketName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建 bucket
     *
     * @param bucketName
     */
    public void makeBucket(String bucketName) {
        try {
            boolean isExist = instance.bucketExists(bucketName);
            if (!isExist) {
                instance.makeBucket(bucketName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     *
     * @param bucketName
     * @param objectName
     * @param filename
     */
    public void putObject(String bucketName, String objectName, String filename) {
        try {
            instance.putObject(bucketName, objectName, filename, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     *
     * @param bucketName
     * @param objectName
     * @param stream
     */
    public void putObject(String bucketName, String objectName, InputStream stream, PutObjectOptions options) {
        try {
            instance.putObject(bucketName, objectName, stream, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     *
     * @param bucketName
     * @param objectName
     */
    public void removeObject(String bucketName, String objectName) {
        try {
            instance.removeObject(bucketName, objectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载
     *
     * @param bucketName
     * @param objectName
     * @param downloadPath
     */
    public void downloadFile(String bucketName, String objectName, String downloadPath) {
        File file = new File(downloadPath);
        try (OutputStream out = new FileOutputStream(file)) {
            InputStream inputStream = instance.getObject(bucketName, objectName);
            byte[] bytes = new byte[1024];
            int byteRead;
            while ((byteRead = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, byteRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载
     *
     * @param bucketName
     * @param objectName
     */
    public InputStream downloadFile(String bucketName, String objectName) {
        try {
            return instance.getObject(bucketName, objectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取下载链接
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param expiry     失效时间（以秒为单位），默认是7天，不得大于七天。
     * @return
     */
    public String presignedGetObject(String bucketName, String objectName, int expiry) {
        try {
            return instance.presignedGetObject(bucketName, objectName, expiry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
