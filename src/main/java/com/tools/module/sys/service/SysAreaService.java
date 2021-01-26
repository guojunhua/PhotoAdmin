package com.tools.module.sys.service;

import com.tools.common.model.Result;
import com.tools.module.sys.entity.SysArea;

import java.util.List;

public interface SysAreaService {

    Result list(SysArea area);

    Result save(SysArea area);

    List<SysArea> select(String parentCode);

    Result delete(String areaCode);

}
