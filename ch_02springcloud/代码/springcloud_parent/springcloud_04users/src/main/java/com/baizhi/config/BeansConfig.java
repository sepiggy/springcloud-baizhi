package com.baizhi.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // 代表这是一个 spring 配置Bean
public class BeansConfig {

    // 工厂中创建 restTemplate
    // @LoadBalanced 注解使得 restTemplate 对象具有负载均衡功能
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
