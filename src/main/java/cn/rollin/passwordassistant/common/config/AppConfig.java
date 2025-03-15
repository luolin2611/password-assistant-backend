package cn.rollin.passwordassistant.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 应用配置类
 *
 * @author rollin
 * @since 2024-12-28 09:28:02
 */
@Configuration
public class AppConfig {
    /**
     * 避免存储的Key乱码，hash 结构依旧会乱码
     *
     * @param factory factory
     * @return redis对象
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        return redisTemplate;
    }
}
