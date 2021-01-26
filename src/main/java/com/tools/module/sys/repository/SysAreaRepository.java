package com.tools.module.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import  org.springframework.stereotype.Repository;
import com.tools.module.sys.entity.SysArea;

/**
 * sys_area Repository
 * Created by 小柒2012
 * Sun Oct 27 12:56:55 CST 2019
*/ 
@Repository 
public interface SysAreaRepository extends JpaRepository<SysArea, Long> {
}

