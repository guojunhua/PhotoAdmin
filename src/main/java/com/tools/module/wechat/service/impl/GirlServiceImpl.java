package com.tools.module.wechat.service.impl;

import com.tools.common.constant.SystemConstant;
import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.module.app.util.AliYunUtils;
import com.tools.module.wechat.entity.Girl;
import com.tools.module.wechat.service.GirlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class GirlServiceImpl implements GirlService {

    @Autowired
    private DynamicQuery dynamicQuery;
    @Autowired
    private AliYunUtils aliYunUtils;
    @Value("${ali-yun.oss.url}")
    private String ossUrl;

    @Override
    public Result list(Integer pageSize, Integer pageNo) {
        String nativeSql = "SELECT * FROM app_girl WHERE status=? GROUP BY id DESC ";
        pageNo = pageNo<=0?0:pageNo-1;
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        List<Girl> list =
                dynamicQuery.nativeQueryPagingList(Girl.class,pageable,nativeSql,new Object[]{SystemConstant.DELETE_STATUS_NO});
        return Result.ok(list);
    }
    @Override
    @Transactional(rollbackFor=Exception.class)
    public void upload(Girl image, File toPic,File fromPic) {
        /**
         * 不需要阿里云OSS注释掉就可以
         * 缩放、正常
         */
        String picName = image.getOssUrl().replace("zoom_","");
        aliYunUtils.upload(fromPic,picName);
        aliYunUtils.upload(toPic,image.getOssUrl());
        image.setOssUrl(ossUrl+"/"+image.getOssUrl());
        dynamicQuery.save(image);
    }

    @Override
    public Result list(Girl girl) {
        String nativeSql = "SELECT COUNT(*) FROM app_girl";
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<Girl> data = new PageBean<>();
        Integer pageNo = girl.getPageNumber();
        pageNo = pageNo<=0?0:pageNo-1;
        if(count>0){
            nativeSql = "SELECT * FROM app_girl ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(pageNo,girl.getPageSize());
            List<Girl> list =  dynamicQuery.nativeQueryPagingList(Girl.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result delete(Long id) {
        String nativeSql = "DELETE FROM app_girl WHERE id=?";
        int count = dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{id});
        if(count==1){
            return Result.ok("成功删除");
        }else{
            return Result.ok("删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result removeAll(Short status) {
        String nativeSql = "UPDATE app_girl SET status=? ";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{status});
        return Result.ok("执行成功");
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result resume(Short status,Long id) {
        String nativeSql = "UPDATE app_girl SET status=? WHERE id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{status,id});
        return Result.ok("执行成功");
    }

}
