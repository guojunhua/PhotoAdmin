package com.tools.module.app.repository;

import com.tools.module.app.entity.AppEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppEmailRepository extends JpaRepository<AppEmail, Long> {

}