package com.ericsson.caffeine;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserService {

    public User getUserNoCache(String userId) {
        return mockQueryUser(userId);
    }

    //sync：设置如果缓存过期是不是只放一个请求去请求数据库，其他请求阻塞，默认是false。
    @Cacheable(value="default",key = "'User-'+#userId", sync = true)
    public User getUser(String userId) {
        return mockQueryUser(userId);
    }

    private User mockQueryUser(String userId){
        User user = new User();
        user.setUserId(userId);
        user.setUserName("tom");
        user.setPhone("13366554928");
        log.info("mockQueryUser executed.....");
        return user;
    }

}
