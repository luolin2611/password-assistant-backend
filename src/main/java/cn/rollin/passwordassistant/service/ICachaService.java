package cn.rollin.passwordassistant.service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 缓存Service
 *
 * @author rollin
 * @since 2024-12-28 09:28:14
 */
public interface ICachaService {
    /**
     * 通过 cacheKey 获取缓存对象
     *
     * @param cacheKey 缓存Key
     * @param <T>      缓存对象类型
     * @return optional对象
     */
    <T> Optional<T> getOptional(String cacheKey);

    /**
     * 通过 cacheKey 获取缓存对象
     *
     * @param cacheKey 缓存Key
     * @param <T>      缓存对象类型
     * @return 缓存对象
     */
    <T> T get(String cacheKey);

    /**
     * 存入缓存
     *
     * @param cacheKey 缓存key
     * @param value    缓存值
     */
    void set(String cacheKey, Object value);

    /**
     * 存入缓存
     *
     * @param cacheKey 缓存key
     * @param value    缓存值
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     */
    void set(String cacheKey, Object value, Long timeout, TimeUnit timeUnit);

    /**
     * 删除缓存
     *
     * @param cacheKey 缓存key
     * @return 删除结果
     */
    boolean delete(String cacheKey);
}
