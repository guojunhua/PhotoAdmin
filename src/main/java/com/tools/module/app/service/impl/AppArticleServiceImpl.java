package com.tools.module.app.service.impl;

import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.common.util.DateUtils;
import com.tools.module.app.repository.AppArticleRepository;
import com.tools.module.app.entity.AppArticle;
import com.tools.module.app.service.AppArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppArticleServiceImpl implements AppArticleService {

    @Autowired
    private DynamicQuery dynamicQuery;
    @Autowired
    private AppArticleRepository articleRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result save(AppArticle article) {
        article.setGmtCreate(DateUtils.getTimestamp());
        article.setGmtModified(DateUtils.getTimestamp());
        articleRepository.saveAndFlush(article);
        return Result.ok("保存成功");
    }

    @Override
    public Result get(Long id) {
        AppArticle article = articleRepository.getOne(id);
        return Result.ok(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Long id) {
        articleRepository.deleteById(id);
        return Result.ok("删除成功");
    }

    @Override
    public Result list(AppArticle article) {
        String nativeSql = "SELECT COUNT(*) FROM app_article";
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<AppArticle> data = new PageBean<>();
        if(count>0){
            nativeSql = "SELECT * FROM app_article ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(article.getPageNo(),article.getPageSize());
            List<AppArticle> list =  dynamicQuery.nativeQueryPagingList(AppArticle.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }
}
