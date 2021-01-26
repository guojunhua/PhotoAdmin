package com.tools.common.util;

import com.alibaba.fastjson.JSONObject;
import com.tools.common.constant.SystemConstant;
import com.tools.common.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author 爪哇笔记
 * @Date 2022/9/4
 * 参考：http://lbsyun.baidu.com/index.php?title=webapi/guide/webservice-geocoding
 */
public class BaiDuMapUtils {

    static Logger logger = LoggerFactory.getLogger(BaiDuMapUtils.class);

    static String AK = "*****************";

    static String SN ="*****************";

    static String STATUS ="status";

    public static void main(String[] args) {
        String dom = "北京王府井";
        System.out.println("'" + dom + "'的经纬度为：" + getCoordinate(dom));
    }

    /**
     * 调用百度地图API根据地址，获取坐标
     * @param address
     * @return
     */
    public static Result getCoordinate(String address) {
        if (address != null && !"".equals(address)) {
            address = address.replaceAll("\\s*", "").replace("#", "栋");
            String url = "http://api.map.baidu.com/geocoding/v3";
            RestTemplate client = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("address",address);
            params.add("output","json");
            params.add("ak",AK);
            params.add("sn",SN);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = client.exchange(url, HttpMethod.POST, requestEntity, String.class);
            String json = response.getBody();
            logger.info("位置信息：{}",json);
            if (StringUtils.isNotBlank(json)) {
                JSONObject obj = JSONObject.parseObject(json);
                if (SystemConstant.CODE_0.equals(obj.getString(STATUS))) {
                    /**
                     * 经度
                     */
                    double longitude = obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
                    /**
                     * 纬度
                     */
                    double latitude = obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
                    DecimalFormat df = new DecimalFormat("#.######");
                    Map<String, Object> map = new HashMap<>(6);
                    map.put("longitude",df.format(longitude));
                    map.put("latitude",df.format(latitude));
                    return Result.ok(map);
                }else{
                    return Result.error(obj.getString("message"));
                }
            }else{
                return Result.error();
            }
        }else{
            return Result.error();
        }
    }
}
