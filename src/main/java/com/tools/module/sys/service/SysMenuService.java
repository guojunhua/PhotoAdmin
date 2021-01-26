package com.tools.module.sys.service;

import com.tools.common.model.Result;
import com.tools.module.sys.entity.SysMenu;

import java.util.List;

public interface SysMenuService {

    Result list(SysMenu menu);

    List<SysMenu> select(Long parentId);

    Result delete(Long menuId);

    List<SysMenu> getByUserId(Long userId);

    Result drop(Long parentId, Long menuId);
}
