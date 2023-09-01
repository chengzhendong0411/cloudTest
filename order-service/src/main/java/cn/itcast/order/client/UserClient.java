
// 通过jar包导入
/*
package cn.itcast.order.client;

import cn.itcast.order.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//
@FeignClient("userservice")
// 部分有效 feign 日志级别
// @FeignClient(value = "userservice",configuration = DefaultFeignConfiguration.class)
public interface UserClient {

    @GetMapping("/user/{id}")
    User findById(@PathVariable("id") Long id);
}
*/
