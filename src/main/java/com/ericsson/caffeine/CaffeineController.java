package com.ericsson.caffeine;

import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.function.Function;

@RestController
public class CaffeineController {
    @Resource
    private UserService userService;

    @Autowired
    private Cache caffeineCache;
    @Autowired
    private CacheManager cacheManager;

    @ResponseBody
    @GetMapping("/caffeine")
    @Cacheable(value = "userCache")
    public String testCaffeine(){
        User user = userService.getUser("1");
        return JSON.toJSONString(user);
    }

    @GetMapping("/getUser")
    @Cacheable(cacheNames = "findUserById", key = "#id", cacheManager = "cacheManager")
    public User findUserById(@RequestParam String id){
        String key = "user_" + id;
        Object obj = caffeineCache.get(key, new Function() {
            @Override
            public Object apply(Object o) {
                return null;
            }
        });
        if (obj == null){
            User user = userService.getUser(id);
            caffeineCache.put(key, user);
            return user;
        }else{
//            return caffeineCache.get(key);
            return null;
        }

    }

}
