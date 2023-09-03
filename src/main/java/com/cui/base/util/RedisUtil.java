package com.cui.base.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 *
 * @author CUI
 * @since 2021-07-17
 */
@Component
@Slf4j
public class RedisUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, String value) {
        try {
            log.debug("key={},value={}", key, value);
            stringRedisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("redis error:", e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, String value, long time) {
        try {
            log.debug("key={},value={},time={}", key, value, time);
            if (time > 0) {
                stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("redis error:", e);
            return false;
        }
    }

    public String get(String key) {
        try {
            log.debug("key={}", key);
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("redis error:", e);
            return null;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return 增长后的数值
     */
    public long incr(String key, long delta) {
        log.debug("key={},delta={}", key, delta);
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 设置过期时间
     *
     * @param key        键
     * @param expireDate 过期时间
     * @return true成功 false 失败
     */
    public boolean expireAt(String key, Date expireDate) {
        log.debug("key={},expireDate={}", key, expireDate);
        return stringRedisTemplate.expireAt(key, expireDate);
    }


    public Long sadd(String key, String value) {
        log.debug("sadd key={},value={}", key, value);
        return stringRedisTemplate.opsForSet().add(key, value);
    }

    /**
     * 发布消息
     *
     * @param channel 消息 topic
     * @param message 消息内容
     */
    public void publish(String channel, String message) {
        // log.debug("channel={},message={}", channel, message);
        stringRedisTemplate.convertAndSend(channel, message);
    }
}
