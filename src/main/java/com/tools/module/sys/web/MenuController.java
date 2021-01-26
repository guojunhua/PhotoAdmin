package com.tools.module.sys.web;

import com.tools.common.model.Result;
import com.tools.common.util.ShiroUtils;
import com.tools.module.sys.entity.SysMenu;
import com.tools.module.sys.repository.SysMenuRepository;
import com.tools.module.sys.service.SysMenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单管理
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012
 */
@Api(tags ="菜单管理")
@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysMenuRepository sysMenuRepository;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(SysMenu menu){
        return sysMenuService.list(menu);
    }

    /**
     * 树结构
     */
    @RequestMapping("/select")
    public Result select(Long parentId){
        List<SysMenu> list = sysMenuService.select(parentId);
        return Result.ok(list);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu menu){
        sysMenuRepository.saveAndFlush(menu);
        return Result.ok("保存成功");
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long menuId){
        return sysMenuService.delete(menuId);
    }


    /**
     * 获取菜单
     */
    @RequestMapping("/getByUser")
    public List<SysMenu> getByUser(){
        return sysMenuService.getByUserId(ShiroUtils.getUserId());
    }


    /**
     * 列表
     */
    @RequestMapping("/drop")
    public Result drop(Long parentId,Long menuId){
        return sysMenuService.drop(parentId,menuId);
    }
}
