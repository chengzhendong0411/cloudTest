package cn.itcast.user.web;

import cn.itcast.user.config.PatternProperties;
import cn.itcast.user.pojo.User;
import cn.itcast.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/user")
// nacos配置热刷新
//方式二：使用@ConfigurationProperties注解
// @RefreshScope
public class UserController {

    @Autowired
    private UserService userService;

    // 方式一、通过页面配置获取到注解方式
   /* @Value("${pattern.dateformat}")
    private String dataformat;

    @GetMapping("now")
    public String now(){
        System.out.println(dataformat);
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dataformat));
    }*/

    //方式二：
    @Autowired
    private PatternProperties properties;

    @GetMapping("/prop")
    public PatternProperties properties(){
        return properties;
    }

    @GetMapping("/now22")
    public String now22(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(properties.getDateformat()));
    }

    /**
     * 路径： /user/110
     *
     * @param id 用户id
     * @return 用户
     */
    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id) {
        return userService.queryById(id);
    }
}
