package org.example.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String INCIDENT_ENTITY = "Incident";

    @Bean
    public CacheManager caffeinCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();


        Caffeine<Object, Object> incidentCacheBuilder = Caffeine.newBuilder()
                .maximumSize(500)
                .weakValues()
                .expireAfterWrite(30, TimeUnit.MINUTES);

        CaffeineCache incidentCache = new CaffeineCache(INCIDENT_ENTITY, incidentCacheBuilder.build());

        cacheManager.setCaches(List.of(incidentCache));

        return cacheManager;
    }

}
