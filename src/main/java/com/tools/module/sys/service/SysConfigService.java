package com.tools.module.sys.service;

import com.tools.common.model.Result;
import com.tools.module.sys.entity.SysConfig;
import com.tools.module.sys.entity.SysUser;

import java.util.List;

public interface SysConfigService {

    Result save(SysConfig config);

    Result get(Long id);

    Result delete(Long id);

    Result list(SysConfig config);
}
