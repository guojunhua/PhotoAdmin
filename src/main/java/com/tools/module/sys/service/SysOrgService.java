package com.tools.module.sys.service;

import com.tools.common.model.Result;
import com.tools.module.sys.entity.SysMenu;
import com.tools.module.sys.entity.SysOrg;
import com.tools.module.sys.entity.SysRole;

import java.util.List;

public interface SysOrgService {

    Result list(SysOrg org);

    Result select(Long parentId);

    Result save(SysOrg org);

    Result delete(Long orgId);

}
