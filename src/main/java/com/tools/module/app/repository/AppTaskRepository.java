package com.tools.module.app.repository;

import com.tools.module.app.entity.AppTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * app_task Repository
 * Created by 小柒2012
 * Sun Oct 27 12:56:55 CST 2019
*/ 
@Repository 
public interface AppTaskRepository extends JpaRepository<AppTask, Long> {

}

