package com.tools.module.sys.service.impl;

import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.module.sys.entity.SysLog;
import com.tools.module.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private DynamicQuery dynamicQuery;

    @Override
    public Result list(SysLog log) {
        String nativeSql = "SELECT COUNT(*) FROM sys_log ";
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<SysLog> data = new PageBean<>();
        if(count>0){
            nativeSql = "SELECT * FROM sys_log ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(log.getPageNo(),log.getPageSize());
            List<SysLog> list =  dynamicQuery.nativeQueryPagingList(SysLog.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }
}
