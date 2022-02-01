package com.baizhi.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baizhi.entity.Product;
import com.baizhi.feignclient.ProductClient;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import lombok.val;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.java2d.pipe.SpanIterator;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@RestController
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private ProductClient productClient;

    @GetMapping("/list")
    public String list() {
        return "list ok";
    }

    // 演示类别服务调用商品服务
    @GetMapping("/category")
    public String category() {
        log.info("category service ....");

        /**
         * OpenFeign 传参方式演示:
         */

//         String result = productClient.test("小陈", 23);

//        String result = productClient.test1(21, "xiaoming");

//        String result = productClient.test2(new Product(1, "超短裙", 23.23, new Date()));

//        val ids = Array.of("21", "23", "24").toJavaArray(String[]::new);
//        val result = productClient.test3(ids);

//        val ids = Array.of("11", "12", "13").toJavaArray(String[]::new);
//        val result = productClient.test4(ids);

        /**
         * OpenFeign 响应处理演示：
         */
//        val product = productClient.product(21);
//        val result = product.toString();

//        val productList = productClient.findByCategoryId(1);
//        val result = List.ofAll(productList).mkString("-");

        var result = productClient.findByCategoryIdAndPage(1, 5, 1);
        // 自定义 JSON 反序列化
        val jsonDict = JSONUtil.toBean(result, Dict.class);
        val total = jsonDict.getInt("total");
        val productList = JSONUtil.toList((JSONArray) jsonDict.get("rows"), Product.class);
        result = StrUtil.format("total={}, productList={}", total, productList);

        return result;
    }

    // 模拟接口调用超时
    @GetMapping("/timeout")
    public void timeout() {
        productClient.timeout();
    }
}
