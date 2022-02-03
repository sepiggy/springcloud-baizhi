package com.baizhi.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// @FeignClient#fallback: 这个属性用来指定当前调用服务不可用时, 默认的备选处理回调函数
@FeignClient(value = "HYSTRIX", fallback = HystrixClientFallBack.class)
public interface HystrixClient {

    @GetMapping("/demo")
    String demo(@RequestParam("id") Integer id);
}
