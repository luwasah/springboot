package com.ericsson.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ali")
public class TestController {
    @RequestMapping("/apppay")
    @CrossOrigin(origins = "*")
    public String getPay(){
        log.info("getPay finished......");
        return "{\"status\": \"success\", \"msg\": \"getPay done!\"}";
    }
}
