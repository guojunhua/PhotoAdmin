package com.tools.module.app.service.impl;

import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.common.util.DateUtils;
import com.tools.module.app.entity.AppEmail;
import com.tools.module.app.repository.AppEmailRepository;
import com.tools.module.app.service.AppEmailService;
import freemarker.template.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppEmailServiceImpl implements AppEmailService {

    @Autowired
    private DynamicQuery dynamicQuery;
    @Autowired
    private AppEmailRepository emailRepository;
    @Autowired
    private JavaMailSender mailSender;//执行者
    @Autowired
    public Configuration configuration;//freemarker
    @Value("${spring.mail.username}")
    public String USER_NAME;//发送者

    static {
        System.setProperty("mail.mime.splitlongparameters","false");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result save(AppEmail email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(USER_NAME);
        message.setTo(email.getReceiveEmail());
        if(StringUtils.isNotBlank(email.getReceiveEmail())){
            message.setCc(email.getReceiveEmail());
        }
        message.setSubject(email.getSubject());
        message.setText(email.getContent());
        mailSender.send(message);
        email.setGmtCreate(DateUtils.getTimestamp());
        emailRepository.save(email);
        return Result.ok("发送成功");
    }

    @Override
    public Result get(Long id) {
        AppEmail email = emailRepository.getOne(id);
        return Result.ok(email);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Long id) {
        emailRepository.deleteById(id);
        return Result.ok("删除成功");
    }

    @Override
    public Result list(AppEmail email) {
        String nativeSql = "SELECT COUNT(*) FROM app_email";
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<AppEmail> data = new PageBean<>();
        if(count>0){
            nativeSql = "SELECT * FROM app_email ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(email.getPageNo(),email.getPageSize());
            List<AppEmail> list =  dynamicQuery.nativeQueryPagingList(AppEmail.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }
}
