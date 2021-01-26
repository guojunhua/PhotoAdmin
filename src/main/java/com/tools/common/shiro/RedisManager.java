package com.tools.common.shiro;


import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.WorkAloneRedisManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * RedisManager
 * @author 小柒2012.zhang
 */
public class RedisManager extends WorkAloneRedisManager implements IRedisManager {

    private RedisProperties redis;

    private JedisPool jedisPool;

    public RedisManager(RedisProperties redis) {
        this.redis = redis;
    }

    private void init() {
        synchronized(this) {
            if (this.jedisPool == null) {
                this.jedisPool = new JedisPool(this.getJedisPoolConfig(), redis.getHost(), redis.getPort(),
                        redis.getTimeout(), redis.getPassword(), redis.getDatabase());
            }
        }
    }

    @Override
    protected Jedis getJedis() {
        if (this.jedisPool == null) {
            this.init();
        }
        return this.jedisPool.getResource();
    }

}
