package cn.rollin.passwordassistant.service.impl;

import cn.rollin.passwordassistant.service.ICachaService;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Redis 缓存类实现
 *
 * @author rollin
 * @since 2024-12-28 09:28:19
 */
@Service(value = "redisCacheSerivce")
public class RedisCacheServiceImpl implements ICachaService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public <T> Optional<T> getOptional(String cacheKey) {
        Optional<T> optional = Optional.empty();
        T t = (T) redisTemplate.opsForValue().get(cacheKey);
        if (ObjectUtils.isNotEmpty(t)) {
            optional = Optional.of(t);
        }
        return optional;
    }

    @Override
    public <T> T get(String cacheKey) {
        return (T) redisTemplate.opsForValue().get(cacheKey);
    }

    @Override
    public void set(String cacheKey, Object value) {
        redisTemplate.opsForValue().set(cacheKey, value);
    }

    @Override
    public void set(String cacheKey, Object value, Long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(cacheKey, value, timeout, timeUnit);
    }

    @Override
    public boolean delete(String cacheKey) {
        return redisTemplate.delete(cacheKey);
    }
}
