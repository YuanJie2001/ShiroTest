package com.vector.springbootshiro.shiro.cache;


import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author WangJiaHui
 * @description: 重写shiroCache实现
 * @ClassName RedisCache
 * @date 2022/4/23 22:07
 */

public class RedisCache implements Cache<String, Object> {
    private final RedisTemplate<String, Object> redisTemplate;

    private final String cacheName;

    public RedisCache(String cacheName, RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.cacheName = cacheName;
    }

    @Override
    public SimpleAuthorizationInfo get(String k) throws CacheException {
        System.out.println("get key:" + k);
        Object o = redisTemplate.opsForHash().get(this.cacheName, k);
        if (o == null) {
            return null;
        } else {
            return JSON.parseObject(o.toString(), SimpleAuthorizationInfo.class);

        }
    }

    @Override
    public Object put(String k, Object v) throws CacheException {
        System.out.println("put key:" + k);
        System.out.println("put value:" + v);
        redisTemplate.opsForHash().put(this.cacheName, k, v);
        return null;
    }


    @Override
    public SimpleAuthorizationInfo remove(String k) throws CacheException {
        SimpleAuthorizationInfo v = get(k);
        redisTemplate.opsForHash().delete(this.cacheName, k);
        return v;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(this.cacheName);
    }

    @Override
    public int size() {
        return redisTemplate.opsForHash().size(this.cacheName).intValue();
    }

    @Override
    public Set<String> keys() {
        return redisTemplate.opsForHash().keys(this.cacheName)
                .stream().map(Objects::toString).collect(Collectors.toSet());
    }

    @Override
    public Collection<Object> values() {
        return redisTemplate.opsForHash().values(this.cacheName)
                .stream().map((json) -> JSON.parseObject(json.toString(), SimpleAuthorizationInfo.class)).collect(Collectors.toList());
    }
}
