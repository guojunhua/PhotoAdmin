package com.tools.module.app.util;

import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileUploader {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException, XmlPullParserException {
        try {
            MinioClient minioClient = new MinioClient("http://minio.52itstyle.vip", "admin", "admin");
            boolean isExist = minioClient.bucketExists("file");
            if(isExist) {
                System.out.println("Bucket already exists.");
            } else {
                minioClient.makeBucket("file");
            }
            /**
             * 多级文件拼接目录就可以
             */
            minioClient.putObject("file","5ea2b3cc33c5d.jpg", "E:\\5ea2b3cc33c5d.jpg",null);
        } catch(MinioException e) {
            System.out.println("Error occurred: " + e);
        }
    }
}