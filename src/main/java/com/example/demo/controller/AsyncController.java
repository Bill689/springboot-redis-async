package com.example.demo.controller;

import com.example.demo.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
public class AsyncController {

    private final Logger log = LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    private AsyncService asyncService;

    @RequestMapping("async")
    public String getAsync(){
        asyncService.getAsync();
        return "使用异步立即返回结果";
    }

    @RequestMapping("asyncWithFuture")
    public Object getAsyncValue(){
        Future<String> future =asyncService.getAsyncValue();
        long time1 = System.currentTimeMillis();
        log.info("等待返回结果");
        String result = null;
        try {
            result = future.get();
            log.info("返回的结果是，{}",result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (System.currentTimeMillis()-time1)/1000;
    }

}
