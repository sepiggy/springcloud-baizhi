package com.baizhi.feignclient;


import com.baizhi.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// 调用商品服务接口
@FeignClient(value = "PRODUCT") // value: 用来书写调用服务服务 id
public interface ProductClient {

    /**
     * OpenFeign 响应处理演示：
     */

    // 声明调用商品服务根据类别id分页查询商品信息以及总条数
    @GetMapping("/productList")
    String findByCategoryIdAndPage(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows, @RequestParam("categoryId") Integer categoryId);

    // 声明调用商品服务根据类别 id 查询一组商品信息
    @GetMapping("/products")
    List<Product> findByCategoryId(@RequestParam("categoryId") Integer categoryId);

    // 声明调用根据id查询商品信息接口
    @GetMapping("/product/{id}")
    Product product(@PathVariable("id") Integer id);

    /**
     * OpenFeign 传参方式演示:
     */

    // 集合类型参数传递
    // 声明调用商品服务中test4接口 传递一个list集合类型参数 test4?ids=21&ids=22
    @GetMapping("/test4")
    String test4(@RequestParam("ids") String[] ids);

    // 数组类型参数传递
    // 声明调用商品服务中 test3 接口
    // 传参方式：/test3?ids=21&ids=22
    @GetMapping("/test3")
    String test3(@RequestParam("ids") String[] ids);

    // 对象类型参数传递 - 使用 JSON 方式传递对象
    // 声明调用商品服务中 test2 接口
    @PostMapping("/test2")
    String test2(@RequestBody Product product);

    // 零散类型参数传递 - 路径参数
    // 声明调用商品服务中 test1 接口
    // 传参方式：/test1/{id}/{name}
    // 这种传参方式需要使用 @PathVariable 注解标注在方法参数上
    @GetMapping("/test1/{id}/{name}")
    String test1(@PathVariable("id") Integer id, @PathVariable("name") String name);

    // 零散类型参数传递 - 请求参数
    // 声明调用商品服务中 test 接口
    // 传参方式: /test?name=xxx&age=23
    // 这种传参方式需要使用 @RequestParam 注解标注在方法参数上
    @GetMapping("/test")
    String test(@RequestParam("name") String name, @RequestParam("age") Integer age);

    // 调用商品服务
    @GetMapping("/product")
    String product();

    @GetMapping("/list")
    String list();

    @GetMapping("/timeout")
    void timeout();
}
