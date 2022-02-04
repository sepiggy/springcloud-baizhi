package com.baizhi.controller;

import cn.hutool.core.util.StrUtil;
import com.baizhi.entity.Product;
import com.baizhi.vos.CollectionVO;
import io.vavr.control.Try;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.TabableView;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Value("${server.port}")
    private int port;

    /**
     * OpenFeign超时处理模拟
     */
    @GetMapping("/timeout")
    public void timeout() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * OpenFeign 响应处理演示：
     */

    // 使用 openFeign 调用服务，并返回复杂包装数据 (Map类型)
    @GetMapping("/productList")
    public Map<String, Object> findByCategoryIdAndPage(Integer page, Integer rows, Integer categoryId) {
        log.info("当前页: {} 每页显示记录数:{} 当前类别id:{} ", page, rows, categoryId);
        // 根据类别 id 分页查询符合当前页集合数据  List<Product>   select * from t_product where categoryId=? limit ?(page-1)*rows,?(rows)
        // 根据类别 id 查询当前类别下总条数       totalCount           select count(id) from t_product where categoryId=?
        val productList = io.vavr.collection.List.of(
                new Product(1, "短裙", 23.23, new Date()),
                new Product(2, "超短裙", 23.23, new Date()),
                new Product(3, "超级超短裙", 23.23, new Date())
        ).toJavaList();
        val total = 1000;
        val resultMap = io.vavr.collection.HashMap.of(
                "rows", productList,
                "total", total
        ).toJavaMap();
        return resultMap;
    }

    // 使用 openFeign 调用服务，并返回集合
    @GetMapping("/products")
    public List<Product> findByCategoryId(Integer categoryId) {
        log.info("类别id: {}", categoryId);
        // 调用业务逻辑根据类别id查询商品列表
        return io.vavr.collection.List.of(
                new Product(1, "短裙", 23.23, new Date()),
                new Product(2, "超短裙", 23.23, new Date()),
                new Product(3, "超级超短裙", 23.23, new Date())
        ).toJavaList();
    }

    // 定义一个接口接收 id 类型参数, 返回一个基于 id 查询的对象
    @GetMapping("/product/{id}")
    public Product product(@PathVariable("id") Integer id) {
        log.info("id:{}", id);
        return new Product(id, "超短连衣裙", 23.23, new Date()); // 模拟去数据库查询数据
    }

    /**
     * OpenFeign 传参方式演示:
     */

    // 定义一个接口接收集合类型参数
    // springmvc中不能直接接收集合类型参数, 如果想要接收集合类型参数必须将集合放入对象中, 使用对象的方式接收才行
    // oo(oriented(面向) object(对象)): 面向对象
    // vo(value object): 用来传递数据对象称之为值对象
    // dto(data transfer(传输) object): 数据传输对象
    @GetMapping("/test4")
    public String test4(CollectionVO collectionVO) {
        val str = io.vavr.collection.List.ofAll(collectionVO.getIds()).mkString("#");
        return str;
    }

    // 定义个接口接收数组类型参数
    @GetMapping("/test3")
    public String test3(String[] ids) {
        val str = io.vavr.collection.List.of(ids).mkString("", ",", "");
        return str;
    }

    // 定义一个接收对象类型参数接口 --- JSON 方式
    @PostMapping("/test2")
    public String test2(@RequestBody Product product) {
        val str = product.toString();
        return str;
    }

    // 定义一个接收零散类型参数接口 --- 路径传递参数
    @GetMapping("/test1/{id}/{name}")
    public String test1(@PathVariable("id") Integer id, @PathVariable("name") String name) {
        val str = StrUtil.format("id:{}, name:{}", id, name);
        return str;
    }

    // 定义一个接收零散类型参数接口 --- queryString
    @GetMapping("/test")
    public String test(String name, Integer age) {
        val str = StrUtil.format("name:{}, age:{}", name, age);
        return str;
    }

    @GetMapping("/product")
    public String product() throws InterruptedException {
        log.info("进入商品服务.....");
        //Thread.sleep(2000);
        return "product ok,当前提供服务端口:" + port;
    }

    @GetMapping("/list")
    public String list(HttpServletRequest request, String color) {
        String header = request.getHeader("User-Name");
        System.out.println("获取对应请求参数 color: " + color);
        System.out.println("获取请求头信息: " + header);
        log.info("商品列表服务");
        return "list ok当前提供服务端口:" + port;
    }
}
