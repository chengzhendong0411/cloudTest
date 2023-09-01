package cn.itcast.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
// 开启定时任务注解
//        @EnableScheduling
// 开启异步调用方法注解
@EnableAsync
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}