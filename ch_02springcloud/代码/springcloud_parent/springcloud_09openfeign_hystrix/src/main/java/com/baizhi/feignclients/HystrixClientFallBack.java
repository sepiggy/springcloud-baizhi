package com.baizhi.feignclients;

import org.springframework.stereotype.Component;

// 自定义 HystrixClient 默认备选处理
@Component
public class HystrixClientFallBack implements HystrixClient {

    @Override
    public String demo(Integer id) {
        return "当前服务不可用, 请稍后再试! id: " + id;
    }
}
