package com.tools.module.sys.service;

import com.tools.common.model.Result;
import com.tools.module.sys.entity.SysRole;

public interface SysRoleService {

    Result list(SysRole role);

    Result select();

    Result save(SysRole role);

    Result delete(Long roleId);

    Result getMenu(Long roleId);

    Result saveMenu(SysRole role);

    Result getOrg(Long roleId);

    Result saveOrg(SysRole role);

}
