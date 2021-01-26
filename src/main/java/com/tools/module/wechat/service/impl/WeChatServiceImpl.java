package com.tools.module.wechat.service.impl;

import com.tools.common.constant.SystemConstant;
import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.common.util.DateUtils;
import com.tools.module.wechat.entity.WeChat;
import com.tools.module.wechat.service.WeChatService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WeChatServiceImpl implements WeChatService {

    @Autowired
    private DynamicQuery dynamicQuery;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result save(WeChat weChat) {
        /**
         * 昵称特殊表情
         */
        weChat.setNickName(EmojiParser.parseToHtmlDecimal(weChat.getNickName()));
        String nativeSql = "SELECT * FROM app_weChat WHERE openid=?";
        WeChat user =
                dynamicQuery.nativeQuerySingleResult(WeChat.class,nativeSql,new Object[]{weChat.getOpenid()});
        if(user!=null){
            weChat.setId(user.getId());
            weChat.setSubscribe(user.getSubscribe());
            dynamicQuery.update(weChat);
        }else{
            weChat.setGmtCreate(DateUtils.getTimestamp());
            weChat.setSubscribe(SystemConstant.SUBSCRIBE_STATUS_NO);
            dynamicQuery.save(weChat);
        }
        return Result.ok("保存成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result subscribe(String openId) {
        /**
         * 每订阅一次加一
         */
        String nativeSql = "UPDATE app_weChat SET subscribe=subscribe+1 WHERE openid=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{openId});
        return Result.ok();
    }

    @Override
    public Result list(WeChat weChat) {
        String nativeSql = "SELECT COUNT(*) FROM app_weChat";
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<WeChat> data = new PageBean<>();
        Integer pageNo = weChat.getPageNumber();
        pageNo = pageNo<=0?0:pageNo-1;
        if(count>0){
            nativeSql = "SELECT * FROM app_weChat ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(pageNo,weChat.getPageSize());
            List<WeChat> list =  dynamicQuery.nativeQueryPagingList(WeChat.class,pageable,nativeSql);
            list.forEach(user-> user.setNickName(EmojiParser.parseToUnicode(user.getNickName())));
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Long id) {
        String nativeSql = "DELETE FROM app_weChat  WHERE id=?";
        dynamicQuery.nativeExecuteUpdate(nativeSql,new Object[]{id});
        return Result.ok("删除成功");
    }

}
