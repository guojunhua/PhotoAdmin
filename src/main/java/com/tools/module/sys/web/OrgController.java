package com.tools.module.sys.web;

import com.tools.common.config.AbstractController;
import com.tools.common.model.Result;
import com.tools.module.sys.entity.SysOrg;
import com.tools.module.sys.service.SysOrgService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机构管理
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012
 */
@Api(tags ="机构管理")
@RestController
@RequestMapping("/sys/org")
public class OrgController extends AbstractController {

    @Autowired
    private SysOrgService sysOrgService;

    /**
     * 机构列表
     */
    @PostMapping("/list")
    public Result list(SysOrg sysOrg){
        return sysOrgService.list(sysOrg);
    }

    /**
     * 树结构
     */
    @RequestMapping("/select")
    public Result select(Long parentId){
        return sysOrgService.select(parentId);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody SysOrg org){
        return sysOrgService.save(org);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long orgId){
        return sysOrgService.delete(orgId);
    }
}
