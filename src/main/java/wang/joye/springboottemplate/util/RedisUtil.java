package wang.joye.springboottemplate.util;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import wang.joye.springboottemplate.constant.TConstants;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 汪继友
 * @since 2020/6/23
 */
@Component
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;

    public RedisUtil(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 存储非string对象时，转化为json格式
     */
    public void set(String key, Object value) {
        set(key, value, TConstants.REDIS_VALID_DAY, TimeUnit.DAYS);
    }

    /**
     * 永久存储
     */
    public void permanentSet(String key, Object value) {
        String v;
        if (value instanceof String)
            v = value.toString();
        else
            v = JSON.toJSONString(value);
        redisTemplate.opsForValue().set(key, v);
    }

    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        String v;
        if (value instanceof String)
            v = value.toString();
        else
            v = JSON.toJSONString(value);
        redisTemplate.opsForValue().set(key, v, timeout, timeUnit);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        String v = get(key);
        return JSON.parseObject(get(key), clazz);
    }

    public Map<String, Object> getMap(String key) {
        String v = get(key);
        return JSON.parseObject(v);
    }

    public <T> List<T> getList(String key, Class<T> clazz) {
        String v = redisTemplate.opsForValue().get(key);
        return JSON.parseArray(v, clazz);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void deleteKeys(String pattern) {
        redisTemplate.delete(redisTemplate.keys(pattern));
    }

    public void keepAlive(String key) {
        if (redisTemplate.getExpire(key, TimeUnit.MINUTES) < 120) {
            redisTemplate.expire(key, 2, TimeUnit.HOURS);
        }
    }
}