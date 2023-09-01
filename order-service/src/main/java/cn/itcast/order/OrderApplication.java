package cn.itcast.order;

import cn.itcast.demo.clients.UserClient;
import cn.itcast.demo.config.DefaultFeignConfiguration;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@MapperScan("cn.itcast.order.mapper")
@SpringBootApplication
// 启动feign  defaultConfiguration定义其级别
// 导入配置文件 defaultConfiguration （全局配置）  局部配置 放到 @FeignClient(value = "userservice", configuration = FeignClientConfiguration.class)
// clients = UserClient.class 因为UserClient不在 OrderApplication 扫描范围内 使用方式1 ： @EnableFeignClients(basePackages = "cn.itcast.feign.clients")
//          使用方式2 ：  clients = UserClient.class
@EnableFeignClients(clients = UserClient.class,defaultConfiguration = DefaultFeignConfiguration.class)
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Bean
    @LoadBalanced  // 负载均衡
    // 实例的权重控制
    //Nacos控制台可以设置实例的权重值，0~1之间
    //同集群内的多个实例，权重越高被访问的频率越高
    //权重设置为0则完全不会被访问
    // 远程服务调用
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    // 负载均衡策略
    @Bean
    public IRule RoundRobinRule(){
        return new RoundRobinRule();
    }
}