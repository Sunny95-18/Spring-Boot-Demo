package com.sjw.test.service.redis;

import com.sjw.test.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/13 16:59
 */
@Component
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String CACHE_USER="CACHE_LOGIN_USER_";
    // ============================String=============================
    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 普通缓存放入-可设置过期时间
     * @param key 键
     * @param value 值
     *@param expire 过期时间
     * @return true成功 false失败
     */
    public boolean set(String key,Object value,Long expire){
        try {
            redisTemplate.opsForValue().set(key, value,expire,TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //获取根据name获取缓存用户
    public User getCacheUser(String name){
        if (StringUtils.isEmpty(name)) {
            return null;
        }
         return (User) redisTemplate.opsForValue().get(CACHE_USER+name);

    }
    //将用户缓存
    public void cacheUser(String name,Object value,Long expire){
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value)) {
            return;
        }
            redisTemplate.opsForValue().set(CACHE_USER+name, value,expire, TimeUnit.SECONDS);//秒单位
    }

}
