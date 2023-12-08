package com.wonders.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class  UserService {

    @Async
    //用Future<>类型返回
    public Future<String> getAsycUser() {
        System.out.println(Thread.currentThread().getName()+":异步用户服务被调用2");

        //结果放进带构造器的Future子类AsyncResult返回
        return new AsyncResult<>("这里是异步用户服务端-2！");
    }

}
