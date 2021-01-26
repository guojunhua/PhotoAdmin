package com.tools.module.sys.web;

import com.tools.common.config.AbstractController;
import com.tools.common.model.Result;
import com.tools.common.util.ShiroUtils;
import com.tools.module.sys.entity.SysUser;
import com.tools.module.sys.service.SysUserService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户管理
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012
 */
@Api(tags ="用户管理")
@RestController
@RequestMapping("/sys/user")
public class UserController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisSessionDAO sessionDAO;

    /**
     * 用户列表
     */
    @PostMapping("/list")
    public Result list(SysUser user){
        return sysUserService.list(user);
    }

    /**
     * 获取
     */
    @PostMapping("/get")
    public Result get(Long userId){
        return sysUserService.get(userId);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody SysUser user){
        return sysUserService.save(user);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long userId){
        return sysUserService.delete(userId);
    }

    /**
     * 修改密码
     */
    @PostMapping("/updatePwd")
    public Result updatePwd(SysUser user){
        return sysUserService.updatePwd(user);
    }

    /**
     * 获取当前用户信息
     */
    @PostMapping("/info")
    public Result info(){
        return sysUserService.get(ShiroUtils.getUserId());
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/update")
    public Result update(@RequestBody SysUser user){
        return sysUserService.update(user);
    }

    /**
     * 用户列表
     */
    @PostMapping("/online")
    public Result online(){
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        List<Map<String, String>> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Session session : sessions) {
            PrincipalCollection principalCollection = (PrincipalCollection) session
                    .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (principalCollection != null) {
                Map<String, String> map = new HashMap<>();
                SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
                map.put("sessionId", session.getId().toString());
                map.put("userName", user.getUsername());
                map.put("nickName", user.getNickname());
                map.put("host", session.getHost());
                map.put("lastAccessTime", sdf.format(session.getLastAccessTime()));
                list.add(map);
            }
        }
        return Result.ok(list);
    }
}
