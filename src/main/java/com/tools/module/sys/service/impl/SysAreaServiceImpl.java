package com.tools.module.sys.service.impl;

import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.Result;
import com.tools.common.util.DateUtils;
import com.tools.module.sys.entity.SysArea;
import com.tools.module.sys.repository.SysAreaRepository;
import com.tools.module.sys.service.SysAreaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysAreaServiceImpl implements SysAreaService {

    @Autowired
    private DynamicQuery dynamicQuery;

    @Autowired
    private SysAreaRepository sysAreaRepository;

    @Override
    public Result list(SysArea area) {
        String nativeSql = "SELECT * FROM sys_area WHERE parent_code=?";
        String description = area.getDescription();
        if(StringUtils.isNotBlank(description)){
            nativeSql +=" AND name like '%"+description+"%'";
        }
        List<SysArea> menuList
                = dynamicQuery.query(SysArea.class,nativeSql,new Object[]{area.getParentCode()});
        return Result.ok(menuList);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    @CacheEvict(cacheNames = {"sys:area:tree"})
    public Result save(SysArea area) {
        String nativeSql = "SELECT * FROM sys_area WHERE area_code=?";
        SysArea sysArea =  dynamicQuery.nativeQuerySingleResult(
                SysArea.class,nativeSql,new Object[]{area.getAreaCode()});
        if(sysArea==null){
            area.setGmtCreate(DateUtils.getTimestamp());
        }else{
            if(!sysArea.getAreaId().equals(area.getAreaId())){
                return Result.error("区域代码重复");
            }
        }
        area.setGmtModified(DateUtils.getTimestamp());
        sysAreaRepository.saveAndFlush(area);
        return Result.ok("保存成功");
    }

    @Override
    @Cacheable(cacheNames = {"sys:area:tree"})
    public List<SysArea> select(String parentCode) {
        String nativeSql = "SELECT * FROM sys_area ";
        if(StringUtils.isNotBlank(parentCode)){
            nativeSql += " WHERE parent_code="+parentCode;
        }
        return dynamicQuery.query(SysArea.class,nativeSql);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    @CacheEvict(cacheNames = {"sys:area:tree"})
    public Result delete(String areaCode) {
        String nativeSql = "SELECT COUNT(*) FROM sys_area WHERE parent_code=?";
        Long count = dynamicQuery.nativeQueryCount(nativeSql,new Object[]{areaCode});
        if(count>0){
            return Result.error("请先删除下级区域");
        }else{
            nativeSql = "DELETE FROM sys_area WHERE area_code=?";
            dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{areaCode});
            return Result.ok("删除成功");
        }
    }
}
