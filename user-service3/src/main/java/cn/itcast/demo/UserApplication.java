package cn.itcast.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  eureka 作用注册中心
 *   消费者如何获取服务提供者具体信息？
 *      服务提供者启动时向eureka注册自己信息
 *      eureka保存这些信息
 *      消费者根据服务名称向eureka拉取提供者信息
 *   消费者如何感知提供者健康状态？
 *      提供者每个30s向eureka发送心跳，报告健康状态。不正常会被删除
 *   多个服务提供者如何选择？
 *      消费者利用负载均衡算法，选择一个
 *
 *    EurekeServer ： 服务端，注册中心
 *      记录服务信息
 *       心跳监控
 *     EurekeClient: 客户端
 *          provider：服务提供者。
 *              注册自己信息到 EurekeServer
 *              每隔30秒发送心跳到 EurekeServer
 *          consumer:服务消费者
 *              根据名称从 EurekeServer 拉取服务列表
 *              基于服务负载均衡，选择一个服务发起远程调用
 *
 *
 *
 *
 *
 */
@MapperScan("cn.itcast.demo.mapper")
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
