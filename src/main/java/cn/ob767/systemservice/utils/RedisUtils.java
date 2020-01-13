package cn.ob767.systemservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<Serializable, Object> redisTemplate;

    /**
     * 读取缓存
     *
     * @param key 主键
     * @return value
     * @author 高希
     * TODO: 2020-01-13
     */
    public Object redisGet(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存
     *
     * @param key   主键
     * @param value 值
     * @param time  缓存时间
     * @return result
     * @author 高希
     * TODO: 2020-01-13
     */
    public boolean redisSet(String key, Object value, Long time) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Map写入缓存
     *
     * @param maps Map键值对
     * @param time 缓存时间
     * @return result
     * @author 高希
     * TODO: 2020-01-13
     */
    public boolean redisSet(Map<String, Object> maps, Long time) {
        boolean result = false;
        try {
            for (Map.Entry<String, Object> map : maps.entrySet()
            ) {
                redisSet(map.getKey(),map.getValue(),time);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新缓存
     *
     * @param key   主键
     * @param value 值
     * @return result
     * @author 高希
     * TODO: 2020-01-13
     */
    public boolean redisGetAndSet(String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新缓存时间
     *
     * @param key  主键
     * @param time 缓存时间
     * @return result
     * @author 高希
     * TODO: 2020-01-13
     */
    public boolean redisUpdateTime(String key, Long time) {
        boolean result = false;
        try {
            Object value = redisGet(key);
            redisSet(key, value, time);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除缓存
     *
     * @param key 主键
     * @return result
     * @author 高希
     * TODO: 2020-01-13
     */
    public boolean redisDelete(String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}