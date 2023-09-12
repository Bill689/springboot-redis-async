package com.example.demo.service;


import java.util.concurrent.Future;

public interface AsyncService {
    void getAsync();

    Future<String> getAsyncValue();
}
