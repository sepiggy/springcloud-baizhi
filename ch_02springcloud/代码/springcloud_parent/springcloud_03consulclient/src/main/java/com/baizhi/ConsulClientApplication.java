package com.baizhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication // 代表这是一个springboot入口应用
@EnableDiscoveryClient  // SpringCloud提供的通用服务注册客户端注解，包括 consul-client, zk-client, nacos-client
public class ConsulClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsulClientApplication.class, args);
    }
}
