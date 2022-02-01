package com.baizhi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired  // 服务注册与发现客户端对象
    private DiscoveryClient discoveryClient;

    @Autowired // 负载均衡客户端对象
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("user")
    public String invokeDemo() {

        log.info("user demo....");

        /** 用户服务调用订单服务的四种方式的演变, 推荐使用 @LoadBalanced 方式 **/

        // 1. 直接使用 RestTemplate 方式
        // 调用订单服务，其服务地址 url: http://localhost:9999/order
        // 必须 GET 方式，接收返回值 String 类型
        // RestTemplate restTemplate = new RestTemplate();
        // String orderResult = restTemplate.getForObject("http://"+randomHost()+"/order", String.class);

        // 使用 ribbon 组件 + RestTemplate 实现负载均衡调用
        // ribbon 提供了以下三个组件可以实现负载均衡，都需要配合 RestTemplate 一起使用
        // 1)DiscoveryClient   2)LoadBalanceClient   3)@LoadBalance

        // 2. 使用 DiscoveryClient + 自定义负载均衡算法的方式
//        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("ORDERS");
//        serviceInstances.forEach(serviceInstance -> {
//            log.info("服务主机: {} 服务端口:{} 服务地址:{}",serviceInstance.getHost(),serviceInstance.getPort(),serviceInstance.getUri());
//        });
        // 这里直接以第一个 uri 作为调用 uri
//        String result = new RestTemplate().getForObject(serviceInstances.get(0).getUri() + "/order", String.class);

        // 3. 使用 LoadBalanceClient 进行服务调用
        // 使用 LoadBalanceClient 直接返回的是经过负载均衡好的服务实例 (默认使用轮询策略），而不是像 DiscoveryClient 返回的是服务实例列表
         ServiceInstance serviceInstance = loadBalancerClient.choose("ORDERS"); // 默认轮询策略
        // log.info("服务地址:{} 服务主机:{} 服务端口:{}",serviceInstance.getUri(),serviceInstance.getHost(),serviceInstance.getPort());
        // String result = restTemplate.getForObject(serviceInstance.getUri() + "/order", String.class);

        // 4.使用 @LoadBalanced 注解, 可以让对象具有 ribbon 负载均衡特性 (推荐使用）
        // url: http://服务名/路径
        // ribbon 缺点：路径写死在代码中不利于维护
        String result = restTemplate.getForObject("http://ORDERS/order", String.class);

        return "ok" + result;
    }

    // 自定义负载均衡随机策略，模拟 Ribbon 的作用
    public String randomHost() {
        List<String> hosts = new ArrayList<>();
        hosts.add("localhost:9999");
        hosts.add("localhost:9990");
        // 生成随机数: 只能在 0-hosts.size() 之间波动
        int i = new Random().nextInt(hosts.size());
        return hosts.get(i);
    }
}
