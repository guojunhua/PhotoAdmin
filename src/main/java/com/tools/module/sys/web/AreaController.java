package com.tools.module.sys.web;

import com.tools.common.model.Result;
import com.tools.module.sys.entity.SysArea;
import com.tools.module.sys.service.SysAreaService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 区域管理
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012
 */
@Api(tags ="区域管理")
@RestController
@RequestMapping("/sys/area")
public class AreaController {

    @Autowired
    private SysAreaService sysAreaService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(SysArea area){
        return sysAreaService.list(area);
    }

    /**
     * 树结构
     */
    @RequestMapping("/select")
    public Result select(String parentCode){
        List<SysArea> list = sysAreaService.select(parentCode);
        return Result.ok(list);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody SysArea area){
        return sysAreaService.save(area);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(String areaCode){
        return sysAreaService.delete(areaCode);
    }

}
