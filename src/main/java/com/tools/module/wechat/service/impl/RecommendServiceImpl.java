package com.tools.module.wechat.service.impl;

import com.tools.common.constant.SystemConstant;
import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.common.util.DateUtils;
import com.tools.module.wechat.entity.Recommend;
import com.tools.module.wechat.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private DynamicQuery dynamicQuery;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result save(Recommend recommend) {
        recommend.setStatus(SystemConstant.DELETE_STATUS_NO);
        recommend.setGmtCreate(DateUtils.getTimestamp());
        recommend.setUuid(UUID.randomUUID().toString());
        recommend.setUserCreate(1);
        recommend.setUsername("小柒2012");
        recommend.setView(1L);
        recommend.setHot(SystemConstant.HOT_STATUS_NO);
        dynamicQuery.save(recommend);
        return Result.ok("保存成功");
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames="recommend",key="#recommend.uuid")
    public Result update(Recommend recommend) {
        recommend.setStatus(SystemConstant.DELETE_STATUS_NO);
        dynamicQuery.update(recommend);
        return Result.ok("修改成功");
    }

    @Override
    public Result list(Recommend recommend) {
        String nativeSql = "SELECT COUNT(*) FROM app_recommend";
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<Recommend> data = new PageBean<>();
        Integer pageNo = recommend.getPageNumber();
        pageNo = pageNo<=0?0:pageNo-1;
        if(count>0){
            nativeSql = "SELECT * FROM app_recommend ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(pageNo,recommend.getPageSize());
            List<Recommend> list =
                    dynamicQuery.nativeQueryPagingList(Recommend.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }

    @Override
    public Result list(Integer pageSize, Integer pageNo) {
        pageNo = pageNo<=0?0:pageNo-1;
        String nativeSql = "SELECT * FROM app_recommend WHERE status=? ORDER BY gmt_create desc";
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        List<Recommend> list =
                dynamicQuery.nativeQueryPagingList(Recommend.class,pageable,nativeSql,new Object[]{SystemConstant.DELETE_STATUS_NO});
        return Result.ok(list);
    }

    @Override
    @Cacheable(cacheNames = {"recommend"})
    public Result get(String uuid) {
        String nativeSql = "SELECT * FROM app_recommend WHERE uuid=?";
        Recommend recommend =
                dynamicQuery.nativeQuerySingleResult(Recommend.class,nativeSql,new Object[]{uuid});
        return Result.ok(recommend);
    }
    @Override
    public Result today() {
        String nativeSql = "SELECT * FROM app_recommend WHERE hot=?";
        Recommend recommend =
                dynamicQuery.nativeQuerySingleResult(Recommend.class,nativeSql,new Object[]{SystemConstant.HOT_STATUS_YES});
        return Result.ok(recommend);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Long id) {
        String nativeSql = "UPDATE app_recommend SET status=? WHERE id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{SystemConstant.DELETE_STATUS_YES,id});
        return Result.ok("删除成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result view(String uuid) {
        String nativeSql = "UPDATE app_recommend SET view=view+1 WHERE uuid=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{uuid});
        return Result.ok();
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result hot(Long id) {
        String nativeSql = "UPDATE app_recommend SET hot=? WHERE hot=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{SystemConstant.HOT_STATUS_NO,SystemConstant.HOT_STATUS_YES});
        nativeSql = "UPDATE app_recommend SET hot=? WHERE id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{SystemConstant.HOT_STATUS_YES,id});
        return Result.ok("推荐成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result removeAll(Short status) {
        String nativeSql = "UPDATE app_recommend SET status=? ";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{status});
        return Result.ok("执行成功");
    }
}
