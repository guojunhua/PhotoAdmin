package com.tools.module.app.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import com.tools.common.constant.SystemConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;

/**
 * 百度智能AI
 * @author 小柒2012
 */
@Component
@Configuration
@EnableConfigurationProperties({BaiDuProperties.class})
public class BaiDuUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(BaiDuUtils.class);

    private BaiDuProperties baiDu;

    public BaiDuUtils(BaiDuProperties baiDu) {
        this.baiDu = baiDu;
    }

    private AipSpeech instance;

    @Value("${file.path}")
    private String filePath;

    @PostConstruct
    public void init() {
        try {
            FileUtil.mkdir(filePath+SystemConstant.SF_FILE_SEPARATOR+"voice");
            instance = new AipSpeech(baiDu.getAppId(), baiDu.getApiKey(), baiDu.getAccessKeySecret());
            // 可选：设置网络连接参数
            instance.setConnectionTimeoutInMillis(2000);
            instance.setSocketTimeoutInMillis(60000);
        } catch (Exception e) {
            LOGGER.error("百度智能AI初始化失败,{}", e.getMessage());
        }
    }

    /**
     * 语音合成
     * 本地测试可能会出现https认证的问题 调用一下 ignoreSsl 方法即可
     * @param text 合成的文本，使用UTF-8编码，请注意文本长度必须小于1024字节
     */
    public String text2Voice(String text,Boolean per) {
        SslUtils.ignoreSsl();
        HashMap<String, Object> options = new HashMap<>();
        if(per){
            options.put("per", "3");
        }else{
            options.put("per", "4");
        }
        TtsResponse res = instance.synthesis(text, "zh", 1, options);
        byte[] data = res.getData();
        if (data != null) {
            try {
                String file = "voice"+SystemConstant.SF_FILE_SEPARATOR+UUID.randomUUID()+".mp3";
                Util.writeBytesToFileSystem(data, filePath + SystemConstant.SF_FILE_SEPARATOR + file);
                return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
