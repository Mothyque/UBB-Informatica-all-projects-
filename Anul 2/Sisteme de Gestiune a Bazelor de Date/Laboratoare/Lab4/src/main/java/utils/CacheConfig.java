package utils;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig
{
    @Bean
    public com.github.benmanes.caffeine.cache.Cache<Object, Object> departmentsCaffeineCache()
    {
        return Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .recordStats()
                .build();
    }

    @Bean
    @Primary
    public CacheManager cacheManager(com.github.benmanes.caffeine.cache.Cache<Object, Object> departmentsCaffeineCache)
    {
        CaffeineCache springCache = new CaffeineCache("departments", departmentsCaffeineCache);

        SimpleCacheManager manager = new SimpleCacheManager();
        List<org.springframework.cache.Cache> caches = new ArrayList<>();
        caches.add(springCache);
        manager.setCaches(caches);
        
        return manager;
    }
}