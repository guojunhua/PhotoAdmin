package com.tools.module.app.service.impl;

import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.common.util.DateUtils;
import com.tools.module.app.entity.AppDingUser;
import com.tools.module.app.service.AppDingUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @Author 爪洼笔记
 * @Date 2022/9/34
 */
@Service("dingUserService")
public class AppDingUserServiceImpl implements AppDingUserService {

    @Autowired
    private DynamicQuery dynamicQuery;

    @Override
    public Result list(AppDingUser user) {
        String countSql = "SELECT COUNT(*) FROM app_ding_user";
        Long totalCount = dynamicQuery.nativeQueryCount(countSql);
        PageBean<AppDingUser> data = new PageBean<>();
        if (totalCount > 0) {
            StringBuffer nativeSql = new StringBuffer();
            nativeSql.append("SELECT * FROM app_ding_user");
            Pageable pageable = PageRequest.of(user.getPageNo(), user.getPageSize());
            List<AppDingUser> list = dynamicQuery.nativeQueryPagingList(AppDingUser.class, pageable, nativeSql.toString());
            data = new PageBean<>(list, totalCount);
        }
        return Result.ok(data);
    }

    @Override
    public List<AppDingUser> listUser() {
        String nativeSql = "SELECT * FROM app_ding_user";
        List<AppDingUser> list = dynamicQuery.query(AppDingUser.class,nativeSql,new Object[]{});
        return list;
    }

    @Override
    public AppDingUser get(Integer userId) {
        String nativeSql = "SELECT * FROM app_ding_user where id=?";
        return dynamicQuery.nativeQuerySingleResult(AppDingUser.class,nativeSql,new Object[]{userId});
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result save(AppDingUser user) {
        String nativeSql = "SELECT * FROM app_ding_user WHERE username=?";
        AppDingUser appDingUser =  dynamicQuery.nativeQuerySingleResult(
                AppDingUser.class,nativeSql,new Object[]{user.getUsername()});
        if(appDingUser!=null){
            if(!appDingUser.getUserId().equals(user.getUserId())){
                return Result.error("用户名重复");
            }
            dynamicQuery.update(user);
        }else{
            user.setGmtCreate(DateUtils.getTimestamp());
            user.setGmtModified(user.getGmtCreate());
            dynamicQuery.save(user);
        }
        return Result.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Integer userId) {
        String nativeSql = "DELETE FROM app_ding_user WHERE id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{userId});
        return Result.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result update(AppDingUser user) {
        dynamicQuery.update(user);
        return Result.ok();
    }
}
