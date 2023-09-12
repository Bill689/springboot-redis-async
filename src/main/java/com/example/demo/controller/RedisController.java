package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("save1")
    @Cacheable(value = "rediscache",key="#root.method.name",condition = "")
    public String save1(){
        redisTemplate.opsForValue().set("20230419","xxxxxx");
        return "save1";
    }

    @GetMapping("save2")
    @CachePut(value = "rediscache",key="#root.method.name")
    public String save2(){
        redisTemplate.opsForValue().set("20230419","yyyyyy");
        return "save2";
    }

    @GetMapping("save3")
    @CacheEvict(value = "rediscache",key="#root.method.name",allEntries = true)  //清除所有缓存
    public String save3(){
        redisTemplate.opsForValue().set("20230419","zzzzzz");
        return "save3";
    }



}
