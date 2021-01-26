package com.tools.module.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 检黄
 * @author 小柒2012
 */
@Component
@Configuration
@EnableConfigurationProperties({YellowProperties.class})
public class YellowUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(YellowUtils.class);

    @Value("${min.io.endpoint}")
    private String minUrl;

    private YellowProperties uCloud;

    public YellowUtils(YellowProperties uCloud) {
        this.uCloud = uCloud;
    }


    /**
     * ucloud 鉴黄
     * @param imageUrl
     * @return
     * 返回值
     * RetCode 0 标识正常 其余一律异常
     * Suggestion 建议， pass-放行， forbid-封禁， check-人工审核
     */
    public String check(String imageUrl) {
        try {
            imageUrl = minUrl +"/"+ imageUrl;
            RestTemplate rest = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            /**
             * 生成signature
             */
            String timestamp = System.currentTimeMillis()+"";
            SortedMap<Object, Object> packageParams = new TreeMap<>();
            packageParams.put("PublicKey", uCloud.getPublicKey());
            packageParams.put("ResourceId", uCloud.getResourceId());
            packageParams.put("Timestamp", timestamp);
            packageParams.put("Url", imageUrl);
            String signature = UCloudUtils.createSign(packageParams,uCloud.getPrivateKey());
            /**
             * 参数
             */
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("Scenes", "porn");
            param.add("Method", "url");
            param.add("Url", imageUrl);
            /**
             * headers 参数
             */
            headers.setContentType(MediaType.parseMediaType("multipart/form-data; charset=UTF-8"));
            headers.set("PublicKey", uCloud.getPublicKey());
            headers.set("Signature",signature);
            headers.set("ResourceId",uCloud.getResourceId());
            headers.set("Timestamp", timestamp);
            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
            ResponseEntity<String> responseEntity = rest.exchange(uCloud.getUrl(), HttpMethod.POST, httpEntity, String.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            LOGGER.error("鉴黄失败，{}",e.getMessage());
            return "";
        }
    }
}
