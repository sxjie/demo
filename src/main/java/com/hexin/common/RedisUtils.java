package com.hexin.common;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Component
public class RedisUtils {


    @Resource
    RedisTemplate<String, Object> redisTemplate;

    public Object get(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, Object value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }
        redisTemplate.opsForValue().set(key, value);
    }


}
