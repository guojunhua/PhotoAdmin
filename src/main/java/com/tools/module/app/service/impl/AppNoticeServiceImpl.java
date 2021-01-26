package com.tools.module.app.service.impl;

import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.common.util.DateUtils;
import com.tools.module.app.repository.AppNoticeRepository;
import com.tools.module.app.entity.AppNotice;
import com.tools.module.app.service.AppNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppNoticeServiceImpl implements AppNoticeService {

    @Autowired
    private DynamicQuery dynamicQuery;
    @Autowired
    private AppNoticeRepository noticeRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result save(AppNotice notice) {
        notice.setGmtCreate(DateUtils.getTimestamp());
        noticeRepository.saveAndFlush(notice);
        return Result.ok("保存成功");
    }

    @Override
    public Result get(Long id) {
        AppNotice notice = noticeRepository.getOne(id);
        return Result.ok(notice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Long id) {
        noticeRepository.deleteById(id);
        return Result.ok("删除成功");
    }

    @Override
    public Result list(AppNotice notice) {
        String nativeSql = "SELECT COUNT(*) FROM app_notice";
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<AppNotice> data = new PageBean<>();
        if(count>0){
            nativeSql = "SELECT * FROM app_notice ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(notice.getPageNo(),notice.getPageSize());
            List<AppNotice> list =  dynamicQuery.nativeQueryPagingList(AppNotice.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }
}
