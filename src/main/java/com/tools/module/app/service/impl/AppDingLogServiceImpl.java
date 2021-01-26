package com.tools.module.app.service.impl;

import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.module.app.entity.AppDingSignInLog;
import com.tools.module.app.service.AppDingLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description 蘑菇钉日志
 * @Author 爪洼笔记
 * @Date 2022/9/34
 */
@Service("dingLogService")
public class AppDingLogServiceImpl implements AppDingLogService {

    @Autowired
    private DynamicQuery dynamicQuery;

    @Override
    public Result listSignInLog(AppDingSignInLog log) {
        String countSql = "SELECT COUNT(*) FROM app_ding_signIn_log";
        Long totalCount = dynamicQuery.nativeQueryCount(countSql);
        PageBean<AppDingSignInLog> data = new PageBean<>();
        if (totalCount > 0) {
            StringBuffer nativeSql = new StringBuffer();
            nativeSql.append("SELECT * FROM app_ding_signIn_log ");
            Object[] params = new Object[]{};
            if (!StringUtils.isEmpty(log.getUsername())) {
                nativeSql.append("WHERE username = ?");
                params = new Object[]{log.getUsername()};
            }
            Pageable pageable = PageRequest.of(log.getPageNo(), log.getPageSize());
            List<AppDingSignInLog> list = dynamicQuery.nativeQueryPagingList(AppDingSignInLog.class, pageable, nativeSql.toString(), params);
            data = new PageBean<>(list, totalCount);
        }
        return Result.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Integer logId) {
        String nativeSql = "DELETE FROM app_ding_signIn_log WHERE id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{logId});
        return Result.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(AppDingSignInLog signInLog) {
        dynamicQuery.save(signInLog);
    }
}
