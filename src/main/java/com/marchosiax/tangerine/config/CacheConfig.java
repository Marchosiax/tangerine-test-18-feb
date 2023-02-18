package com.marchosiax.tangerine.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfig.class);

    @Bean
    public CacheManager productCacheManager() {
        return new ConcurrentMapCacheManager("product");
    }

    @Bean
    public Cache productCache(CacheManager cacheManager) {
        LOGGER.info("Initializing cache for products");
        var cache = cacheManager.getCache("product");
        if (cache == null)
            throw new IllegalStateException("Cache for 'product' not found");
        return cache;
    }

}
