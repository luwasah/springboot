package com.ericsson.caffeine;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Caffeine入门学习
 */
@RestController
public class CaffeineController {
    @Resource
    private UserService userService;

    @ResponseBody
    @GetMapping("/caffeine")
    public String testCaffeine(){
        User user = userService.getUserNoCache("1");
        return JSON.toJSONString(user);
    }

}
