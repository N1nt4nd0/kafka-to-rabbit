package org.kafka.practice.kafkademo.domain.config.cache;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@EnableCaching
@Configuration
@RequiredArgsConstructor
public class RedisCacheConfig {

    private final RedisConnectionFactory connectionFactory;

    @PostConstruct
    protected void clearCacheOnInit() {
        connectionFactory.getConnection().commands().flushAll();
    }

}