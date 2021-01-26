package com.tools.module.app.service.impl;

import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.Result;
import com.tools.module.app.service.AppQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AppQueryServiceImpl implements AppQueryService {

	@Autowired
	private DynamicQuery dynamicQuery;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Result crud(String sql) {
        if(sql.startsWith("SELECT")||sql.startsWith("select")){
            List<Map<String,String>> list
                    = dynamicQuery.nativeQueryListMap(sql,null);
            return Result.ok(list);
        }else{
            int count = dynamicQuery.nativeExecuteUpdate(sql);
            return Result.ok(count);
        }
    }
}
