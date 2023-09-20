package cn.itcast.order.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RedissonConfig
 *
 * @author penglei@133.cn
 * @date 2023/8/21 16:34
 */
@Configuration
public class RedissonConfig {

    @Bean
    @ConditionalOnProperty({"redisson.redis.address", "redisson.redis.password"})
    public RedissonClient redissonClient(@Value("${redisson.redis.address}") String redisAddress,
                                         @Value("${redisson.redis.password}") String redisPassword) {
        Config config = new Config();
        // 目前是单点的
        config.useSingleServer()
                .setAddress(redisAddress)
                .setPassword(redisPassword)
                // 从节点最小空闲连接数
                .setConnectionMinimumIdleSize(10)
                // 连接池大小
                .setConnectionPoolSize(128)
                // DNS监测时间间隔，单位：毫秒
                .setDnsMonitoringInterval(5000)
                // 从节点发布和订阅连接的最小空闲连接数
                .setSubscriptionConnectionMinimumIdleSize(1)
                //从节点发布和订阅连接池大小
                .setSubscriptionConnectionPoolSize(50)
                // 单个连接最大订阅数量
                .setSubscriptionsPerConnection(5)
                // 命令失败重试次数
                .setRetryAttempts(3)
                // 命令重试发送时间间隔，单位：毫秒
                .setRetryInterval(1500)
                // Redis服务器响应超时，单位：毫秒
                .setTimeout(3000)
                // 连接到Redis服务器时超时
                .setConnectTimeout(30000)
                // 连接最大空闲时间时间
                .setIdleConnectionTimeout(10000)
                .setPingConnectionInterval(0);
        return Redisson.create(config);
    }
}
