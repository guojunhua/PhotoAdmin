package com.tools.module.sys.repository;

import com.tools.module.sys.entity.SysConfig;
import com.tools.module.sys.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * sys_user Repository
 * Created by 小柒2012
 * Sun Oct 27 13:03:00 CST 2019
*/ 
@Repository 
public interface SysConfigRepository extends JpaRepository<SysConfig, Long> {

}

