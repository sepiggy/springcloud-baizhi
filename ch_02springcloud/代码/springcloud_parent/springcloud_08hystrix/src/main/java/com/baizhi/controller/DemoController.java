package com.baizhi.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class DemoController {

    @GetMapping("/demo1")
    @HystrixCommand(defaultFallback = "defaultFallBack")
    public String demo1() {
        System.out.println("demo1 ok!!");
        return "demo1 ok !!!";
    }

    /**
     * 这里演示的是使用 Hystrix 完成服务提供方熔断
     * 还可以使用 Hystrix 配合 OpenFeign 完成服务调用方异常快速响应
     */
    @GetMapping("demo")
    // 熔断之后处理
    // fallbackMethod属性：该方法失败回调函数的方法名
    // defaultFallBack属性：默认失败回调函数的方法名
    /**
     * @defaultFallBack属性注释：
     * Specifies default fallback method for the command. If both {@link #fallbackMethod} and {@link #defaultFallback}
     * methods are specified then specific one is used.
     * note: default fallback method cannot have parameters, return type should be compatible with command return type.
     */
    @HystrixCommand(fallbackMethod = "demoFallBack", defaultFallback = "defaultFallback")
    public String demo(Integer id) { // ?id =
        System.out.println(LocalDateTime.now());
        if (id <= 0) {
            throw new RuntimeException("无效id!!!!");
        }
        return "demo ok !!!";
    }

    // 默认的处理方法
    public String defaultFallBack() {
        return "网络连接失败,请重试!!!";
    }

    // 自己备选处理，备选方法与需要熔断功能方法的参数列表保持一致
    public String demoFallBack(Integer id) {
        return "当前活动过于火爆,服务已经被熔断了!!!";
    }
}
