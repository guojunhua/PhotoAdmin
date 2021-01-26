package com.tools.module.app.service.impl;

import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.module.app.entity.AppImage;
import com.tools.module.app.service.AppImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppImageServiceImpl implements AppImageService {

    @Autowired
    private DynamicQuery dynamicQuery;

    @Override
    @Transactional
    public void upload(AppImage image) {
        dynamicQuery.save(image);
    }

    @Override
    public Result list(AppImage image) {
        String nativeSql = "SELECT COUNT(*) FROM app_image ";
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<AppImage> data = new PageBean<>();
        if(count>0){
            nativeSql = "SELECT * FROM app_image ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(image.getPageNo(),image.getPageSize());
            List<AppImage> list =  dynamicQuery.nativeQueryPagingList(AppImage.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }
}
