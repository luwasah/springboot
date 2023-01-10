package com.ericsson.caffeine;

import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {
    @Bean
    public Cache cache(CaffeineCacheManager cacheManager){
        return cacheManager.getCache("default");
    }
}

