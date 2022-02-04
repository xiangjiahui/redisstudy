package com.xjh.redisstudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author xjh
 * @date 2022/1/30 13:10
 */
@RestController
public class RedisController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;



    @GetMapping("/redisTest")
    public String redisTest(){
        redisTemplate.opsForValue().set("xjh","向嘉晖");
        String name = (String) redisTemplate.opsForValue().get("xjh");
        return name;
    }
}
