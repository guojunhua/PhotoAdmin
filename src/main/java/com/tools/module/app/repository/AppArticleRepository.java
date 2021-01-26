package com.tools.module.app.repository;

import com.tools.module.app.entity.AppArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppArticleRepository extends JpaRepository<AppArticle, Long> {

}