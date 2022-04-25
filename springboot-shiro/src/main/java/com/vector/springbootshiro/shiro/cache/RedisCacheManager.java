package com.vector.springbootshiro.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author WangJiaHui
 * @description: 自定义shiro的redis缓存管理器
 * @ClassName RedisCachemanager
 * @date 2022/4/23 21:58
 */
@Component
public class RedisCacheManager implements CacheManager {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 参数1: 认证或授权缓存的统一名称
     */
    @Override
    public RedisCache getCache(String cacheName) throws CacheException {
        System.out.println(cacheName);
        // 获取RedisCache 实例
        return new RedisCache(cacheName, redisTemplate);
    }
}
