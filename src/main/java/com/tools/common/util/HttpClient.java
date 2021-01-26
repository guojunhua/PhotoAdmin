package com.tools.common.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class HttpClient {

    /**
     * 校验网址正确性
     * @param url
     * @return
     */
    public static Integer client(String url){
       try{
           HttpHeaders headers = new HttpHeaders();
           headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
           HttpEntity<Resource> httpEntity = new HttpEntity<>(headers);
           RestTemplate client = new RestTemplate();
           ResponseEntity<String> response = client.exchange(url, HttpMethod.GET, httpEntity , String.class);
           return response.getStatusCode().value();
       }catch (Exception e){
            e.printStackTrace();
       }
       return 0;
    }
}

