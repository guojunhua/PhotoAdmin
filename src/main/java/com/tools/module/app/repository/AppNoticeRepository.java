package com.tools.module.app.repository;

import com.tools.module.app.entity.AppNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppNoticeRepository extends JpaRepository<AppNotice, Long> {

}