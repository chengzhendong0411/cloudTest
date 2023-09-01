package cn.itcast.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*

网关搭建步骤：
        创建项目，引入nacos服务发现和gateway依赖
        配置application.yml，包括服务基本信息、nacos地址、路由
        路由配置包括：
        路由id：路由的唯一标示
        路由目标（uri）：路由的目标地址，http代表固定地址，lb代表根据服务名负载均衡
        路由断言（predicates）：判断路由的规则，
        路由过滤器（filters）：对请求或响应做处理
*/

@SpringBootApplication
public class GeteWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeteWayApplication.class,args);
    }
}