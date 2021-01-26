package com.tools.module.wechat.api;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.tools.common.model.Result;
import com.tools.module.wechat.entity.WeChat;
import com.tools.module.wechat.model.LoginRequest;
import com.tools.module.wechat.service.PushService;
import com.tools.module.wechat.service.WeChatService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 微信小程序
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012
 */
@RestController
@RequestMapping("minApp/weChat")
public class WeChatApi {

    @Autowired
    private WxMaService wxService;

    @Autowired
    private WeChatService weChatService;

    @Autowired
    private PushService pushService;

    /**
     * 验证签名
     * @return
     */
    @RequestMapping("/msg")
    public String msg(@RequestParam String signature, @RequestParam String timestamp,
                      @RequestParam String nonce, @RequestParam String echostr) {
        Boolean flag = wxService.checkSignature(timestamp, nonce, signature);
        if (flag) {
            return echostr;
        } else {
            return "";
        }
    }

    /**
     * 登录验证
     * @return
     */
    @RequestMapping("/login")
    public Result login(LoginRequest request) throws WxErrorException {
        WxMaJscode2SessionResult session =
                wxService.getUserService().getSessionInfo(request.getCode());
        if (null == session) {
            throw new RuntimeException("login handler error");
        }
        return Result.ok(session);
    }
    /**
     * 获取用户
     * @return
     */
    @RequestMapping("/getUser")
    public Result getUser(LoginRequest request) throws WxErrorException {
        WxMaJscode2SessionResult session =
                wxService.getUserService().getSessionInfo(request.getCode());
        if (null == session) {
            return Result.error("login handler error");
        }
        // 解密用户信息
        WxMaUserInfo wxUserInfo = wxService.getUserService().getUserInfo(session.getSessionKey(),
                request.getEncryptedData(), request.getIv());
        if (null == wxUserInfo) {
            return Result.error("wxUser not exist");
        }
        return Result.ok(wxUserInfo);
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    @RequestMapping("/save")
    public Result save(WeChat user) {
        return weChatService.save(user);
    }

    /**
     * 订阅消息
     * @param openId
     * @return
     */
    @RequestMapping("/subscribe")
    public Result subscribe(String openId) {
        return weChatService.subscribe(openId);
    }

    /**
     * 推送消息
     *
     * @return
     */
    @RequestMapping("/push")
    public void push() {
        pushService.push();
    }

}
