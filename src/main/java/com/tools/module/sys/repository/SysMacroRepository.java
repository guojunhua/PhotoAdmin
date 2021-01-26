package com.tools.module.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import  org.springframework.stereotype.Repository;
import com.tools.module.sys.entity.SysMacro;

/**
 * sys_macro Repository
 * Created by 小柒2012
 * Sun Oct 27 13:01:25 CST 2019
*/ 
@Repository 
public interface SysMacroRepository extends JpaRepository<SysMacro, Long> {
}

