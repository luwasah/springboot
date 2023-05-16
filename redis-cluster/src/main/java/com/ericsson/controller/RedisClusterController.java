package com.ericsson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisClusterController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @ResponseBody
    @PostMapping("/insert")
    public String insert(String username){
        redisTemplate.opsForValue().set("username", username);
        return "插入成功";
    }

    @ResponseBody
    @PostMapping("/get")
    public String get(){
        Object result = redisTemplate.opsForValue().get("username");
        return "查询结果: " + result;
    }

    @ResponseBody
    @DeleteMapping("/remove")
    public String remove(String key){
        Boolean result = redisTemplate.delete(key);
        if(result){
            return "删除成功!";
        }
        return "删除失败!";
    }

}
