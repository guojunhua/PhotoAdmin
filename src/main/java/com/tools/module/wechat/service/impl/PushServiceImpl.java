package com.tools.module.wechat.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeData;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import com.tools.common.constant.SystemConstant;
import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.Result;
import com.tools.common.util.DateUtils;
import com.tools.module.wechat.entity.Recommend;
import com.tools.module.wechat.model.PushModel;
import com.tools.module.wechat.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 推送消息
 * 参数说明：https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.send.html
 */
@Service
public class PushServiceImpl implements PushService {

    @Autowired
    private WxMaService wxService;

    @Autowired
    private DynamicQuery dynamicQuery;

    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    private static ThreadPoolExecutor executor  =
            new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(100));


    @Override
    public Result push() {
        PushModel model = new PushModel();
        model.setPage("pages/history/history");
        model.setOpenid("oCay55YrmfTa5E-NI9pmE3ZiXeyc");
        model.setTemplateId("Qwe3JQrNVwVqBpfeqSxOtp-PdpbCHqYgzq1OJg7HaEo");
        model.setTitle("妹纸在吃的道路上");
        model.setTime(DateUtils.getTime());
        model.setAuthor("小柒");
        push(model);
        return Result.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result today(Long id) {
        String nativeSql = "SELECT * FROM app_recommend WHERE id=?";
        Recommend recommend =
                dynamicQuery.nativeQuerySingleResult(Recommend.class,nativeSql,new Object[]{id});
        nativeSql = "SELECT openid FROM app_weChat WHERE subscribe>=?";
        List<String> list = dynamicQuery.query(nativeSql,new Object[]{SystemConstant.SUBSCRIBE_STATUS_YES});
        list.forEach(openid->{
            /**
             * 推送一次减少一次
             */
            String subSql = "UPDATE app_weChat SET subscribe=subscribe-1 WHERE openid=?";
            dynamicQuery.nativeExecuteUpdate(subSql,new Object[]{openid});
            /**
             * 推送
             */
            PushModel model = new PushModel();
            model.setPage("pages/detail/detail?uuid="+recommend.getUuid()+"&title="+recommend.getTitle());
            model.setOpenid(openid);
            model.setTemplateId("Qwe3JQrNVwVqBpfeqSxOtp-PdpbCHqYgzq1OJg7HaEo");
            model.setTitle(recommend.getTitle());
            model.setTime(DateUtils.getTime());
            model.setAuthor(recommend.getCategory());
            push(model);
        });
        return Result.ok("推送成功");
    }

    public void push(PushModel model) {
        Runnable task = () -> {
            WxMaSubscribeMessage subscribeMessage = new WxMaSubscribeMessage();
            //跳转小程序页面路径
            subscribeMessage.setPage(model.getPage());
            //模板消息id
            subscribeMessage.setTemplateId(model.getTemplateId());
            //给谁推送 用户的openid
            subscribeMessage.setToUser(model.getOpenid());
            ArrayList<WxMaSubscribeData> wxMaSubscribeData = new ArrayList<>();

            //第一个内容：更新内容
            WxMaSubscribeData wxMaSubscribeData1 = new WxMaSubscribeData();
            wxMaSubscribeData1.setName("thing1");
            wxMaSubscribeData1.setValue(model.getTitle());
            wxMaSubscribeData.add(wxMaSubscribeData1);

            // 第二个内容：时间
            WxMaSubscribeData wxMaSubscribeData2 = new WxMaSubscribeData();
            wxMaSubscribeData2.setName("date2");
            wxMaSubscribeData2.setValue(model.getTime());
            wxMaSubscribeData.add(wxMaSubscribeData2);
            // 第三个内容：作者
            WxMaSubscribeData wxMaSubscribeData3 = new WxMaSubscribeData();
            wxMaSubscribeData3.setName("name3");
            wxMaSubscribeData3.setValue(model.getAuthor());
            wxMaSubscribeData.add(wxMaSubscribeData3);
            //把集合给大的data
            subscribeMessage.setData(wxMaSubscribeData);
            try {
                //进行推送
                wxService.getMsgService().sendSubscribeMsg(subscribeMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        executor.execute(task);
    }
}
