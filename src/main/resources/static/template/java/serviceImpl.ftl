package com.tools.module.${prefix}.service.impl;

import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.common.util.DateUtils;
import com.tools.module.${prefix}.repository.${entityName}Repository;
import com.tools.module.${prefix}.entity.${entityName};
import com.tools.module.${prefix}.service.${entityName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ${entityName}ServiceImpl implements ${entityName}Service {

    @Autowired
    private DynamicQuery dynamicQuery;
    @Autowired
    private ${entityName}Repository ${function}Repository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result save(${entityName} ${function}) {
        ${function}.setGmtCreate(DateUtils.getTimestamp());
        ${function}.setGmtModified(DateUtils.getTimestamp());
        ${function}Repository.saveAndFlush(${function});
        return Result.ok("保存成功");
    }

    @Override
    public Result get(Long id) {
        ${entityName} ${function} = ${function}Repository.getOne(id);
        return Result.ok(${function});
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Long id) {
        ${function}Repository.deleteById(id);
        return Result.ok("删除成功");
    }

    @Override
    public Result list(${entityName} ${function}) {
        String nativeSql = "SELECT COUNT(*) FROM ${tableName}";
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<${entityName}> data = new PageBean<>();
        if(count>0){
            nativeSql = "SELECT * FROM ${tableName} ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(${function}.getPageNo(),${function}.getPageSize());
            List<${entityName}> list =  dynamicQuery.nativeQueryPagingList(${entityName}.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }
}
