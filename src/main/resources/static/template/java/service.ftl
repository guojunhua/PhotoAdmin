package com.tools.module.${prefix}.service;

import com.tools.common.model.Result;
import com.tools.module.${prefix}.entity.${entityName};

public interface ${entityName}Service {

    Result get(Long id);

    Result save(${entityName} ${function});

    Result delete(Long task);

    Result list(${entityName} ${function});
}
