package com.ericsson.caffeine;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Autowired
    UserService userService;

    /**
     * 创建CacheManager bean并且配置缓存过期时间和缓存最大大小;然后@Bean将其自动注入到spring容器中
     * @return
     */
    @Bean(name = "cacheManager")
    public CacheManager cacheManagerWithCaffeine(@Qualifier("cacheLoader") CacheLoader cacheLoader) {
        Caffeine caffeine = Caffeine.newBuilder()
                //cache的初始容量值
                .initialCapacity(100)
                //maximumSize用来控制cache的最大缓存数量，maximumSize和maximumWeight(最大权重)不可以同时使用，
                .maximumSize(1000)
                //最后一次写入或者访问后过久过期
                .expireAfterAccess(500, TimeUnit.SECONDS)
                //创建或更新之后多久刷新,需要设置cacheLoader
                .refreshAfterWrite(10, TimeUnit.SECONDS);

        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        cacheManager.setCacheLoader(cacheLoader);
        //cacheManager.setCacheNames(names);//根据名字可以创建多个cache，但是多个cache使用相同的策略
        cacheManager.setAllowNullValues(false);//是否允许值为空
        return cacheManager;
    }

    @Bean("caffeineCache")
    public LoadingCache getCache(@Qualifier("cacheLoader") CacheLoader cacheLoader) {

        return Caffeine.newBuilder()
                //cache的初始容量值
                .initialCapacity(100)
                //maximumSize用来控制cache的最大缓存数量，maximumSize和maximumWeight(最大权重)不可以同时使用，
                .maximumSize(1000)
                //最后一次写入或者访问后过久过期
                .expireAfterAccess(500, TimeUnit.SECONDS)
                //创建或更新之后多久刷新,需要设置cacheLoader
                .refreshAfterWrite(10, TimeUnit.SECONDS).build(cacheLoader);
    }

    /**
     * 必须要指定这个Bean，refreshAfterWrite配置属性才生效
     */
    @Bean(name = "cacheLoader")
    public CacheLoader<Object, Object> cacheLoader() {
        return new CacheLoader<Object, Object>() {

            //第一次请求访问这个方法，这时缓存为空，可以在这时去请求数据库填满缓存
            @Override
            public Object load(Object key) throws Exception {
                User user = userService.getUser((String)key);
                return user;
            }

            // 过期刷新缓存是回调这个方法，这时候应该去请求数据库可以根据自己的需需求设计缓存对象或以map形式保存，然后在key过期时去请求数据库刷新缓存对象或map
            @Override
            public Object reload(Object key, Object oldValue) throws Exception {
                System.out.println("--refresh--:" + key);
                User user = userService.getUser((String)key);
                return user;
            }
        };
    }
}
