package com.tools.common.shiro;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro权限配置
 * 爪哇笔记：https://blog.52itstyle.vip
 * @author 小柒2012.zhang
 */
@Configuration
@EnableConfigurationProperties({RedisProperties.class})
public class ShiroConfig {

    private RedisProperties redis;

    public ShiroConfig(RedisProperties redis) {
        this.redis = redis;
    }

    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean (SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        filtersMap.put("kickout", kickoutSessionControlFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        /**
         * 静态文件
         */
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/lib/**","anon");
        filterChainDefinitionMap.put("/data/**","anon");
        filterChainDefinitionMap.put("/images/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/file/**","anon");

        /**
         * swagger2
         */
//        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
//        filterChainDefinitionMap.put("/swagger-resources", "anon");
//        filterChainDefinitionMap.put("/v2/api-docs", "anon");
//        filterChainDefinitionMap.put("/webjars/springfox-swagger-ui/**", "anon");
//        filterChainDefinitionMap.put("/configuration/security", "anon");
//        filterChainDefinitionMap.put("/configuration/ui", "anon");

        /**
         * 登录注册
         */
        filterChainDefinitionMap.put("/login.html","anon");
        filterChainDefinitionMap.put("/register.html","anon");
        filterChainDefinitionMap.put("/forget.html","anon");
        filterChainDefinitionMap.put("/sys/logout","anon");
        filterChainDefinitionMap.put("/sys/login","anon");
        filterChainDefinitionMap.put("/sys/register","anon");
        /**
         * 短链
         */
        filterChainDefinitionMap.put("/r/**","anon");
        /**
         * 小程序
         */
        filterChainDefinitionMap.put("/minApp/**","anon");
        /**
         * 管理后台
         */
        filterChainDefinitionMap.put("/sys/**", "roles[admin]");
        filterChainDefinitionMap.put("/**", "kickout,authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        /**
         * 不需要缓存 每次实时读取数据库
         * securityManager.setCacheManager(cacheManager());
         */
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        /**
         * 如果这里赋值，默认会使用这里的参数
         */
        long time = 2*60*60*1000;
        sessionManager.setGlobalSessionTimeout(time);
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager(redis);
        return redisManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现
     * 原理就是重写 AbstractSessionDAO
     * 有兴趣的小伙伴自行阅读源码
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setKeyPrefix("shiro:session:cloudbed:");
        return redisSessionDAO;
    }
    /**
     * 限制同一账号登录同时登录人数控制
     *
     * @return
     */
    @Bean
    public KickoutSessionControlFilter kickoutSessionControlFilter() {
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        kickoutSessionControlFilter.setCacheManager(cacheManager());
        kickoutSessionControlFilter.setSessionManager(sessionManager());
        kickoutSessionControlFilter.setKickoutAfter(false);
        kickoutSessionControlFilter.setMaxSession(10);
        kickoutSessionControlFilter.setKickoutUrl("/login.html");
        return kickoutSessionControlFilter;
    }

}