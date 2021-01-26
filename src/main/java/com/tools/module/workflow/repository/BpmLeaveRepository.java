package com.tools.module.workflow.repository;

import com.tools.module.workflow.entity.BpmLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BpmLeaveRepository extends JpaRepository<BpmLeave, Long> {

}