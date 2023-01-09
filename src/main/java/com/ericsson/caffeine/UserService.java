package com.ericsson.caffeine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    public User getUser(String userId){
        User user = new User();
        user.setUserId(userId);
        user.setUserName("tom");
        user.setPhone("13366554927");
        return user;
    }

}
