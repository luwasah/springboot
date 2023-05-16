package com.ericsson.sentinel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedisSentinel {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testQuery(){
        String result = redisTemplate.opsForValue().get("name");
        System.out.println("---------------result: " + result);
    }

}
