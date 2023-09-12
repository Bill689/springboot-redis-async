package com.example.demo.service.impl;

import com.example.demo.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class AsyncServiceImpl implements AsyncService {

    @Override
    @Async("asyncPool")
    public void getAsync() {
        try {
            Thread.sleep(10000);
            System.out.println("执行完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    @Async("asyncPool")
    public Future<String> getAsyncValue() {

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new AsyncResult<>("执行完毕");
    }
}
