package com.tools.module.app.service.impl;

import cn.hutool.db.Page;
import com.tools.common.constant.SystemConstant;
import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.common.util.DateUtils;
import com.tools.common.util.TinyUrlUtil;
import com.tools.module.app.entity.AppTinyUrl;
import com.tools.module.app.service.AppTinyUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppTinyUrlServiceImpl implements AppTinyUrlService {

    @Autowired
    private DynamicQuery dynamicQuery;
    @Autowired
    private TinyUrlUtil tinyUrlUtil;
    @Value("${project.url}")
    private String url;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result save(AppTinyUrl tinyUrl) {
        String[] keys =  tinyUrlUtil.ShortText(tinyUrl.getUrl());
        Integer hashKey = tinyUrl.getUrl().hashCode();
        String nativeSql = "SELECT * FROM app_tiny_url WHERE hash_key=?";
        AppTinyUrl tiny = dynamicQuery.nativeQuerySingleResult(AppTinyUrl.class,nativeSql,new Object[]{hashKey});
        if(tiny!=null){
            return Result.ok(url +"/r/"+ tiny.getTinyKey());
        }
        tinyUrl.setTinyKey(keys[0]);
        tinyUrl.setView((long)0);
        tinyUrl.setType((short)0);
        tinyUrl.setDelStatus(SystemConstant.DELETE_STATUS_NO);
        tinyUrl.setGmtCreate(DateUtils.getTimestamp());
        tinyUrl.setGmtExpire(DateUtils.getTimestamp());
        tinyUrl.setHashKey(hashKey);
        dynamicQuery.save(tinyUrl);
        return Result.ok(url +"/r/"+ tinyUrl.getTinyKey());
    }

    @Override
    public String getUrl(String tinyKey) {
        String nativeSql = "SELECT * FROM app_tiny_url WHERE tiny_key=?";
        AppTinyUrl tiny = dynamicQuery.nativeQuerySingleResult(AppTinyUrl.class,nativeSql,new Object[]{tinyKey});
        String url = "";
        if(tiny!=null){
            url = tiny.getUrl();
        }
        return url;
    }

    @Override
    public Result list(Page page) {
        String nativeSql = "SELECT COUNT(*) FROM app_tiny_url ";
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<AppTinyUrl> data = new PageBean<>();
        if(count>0){
            nativeSql = "SELECT * FROM app_tiny_url ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(page.getPageNumber()-1,page.getPageSize());
            List<AppTinyUrl> list =  dynamicQuery.nativeQueryPagingList(AppTinyUrl.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }

}
