package com.tools.module.${prefix}.repository;

import com.tools.module.${prefix}.entity.${entityName};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ${entityName}Repository extends JpaRepository<${entityName}, Long> {

}