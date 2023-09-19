package cn.itcast.user.web;

import cn.itcast.user.config.PatternProperties;
import cn.itcast.user.pojo.User;
import cn.itcast.user.service.UserService;
import cn.itcast.user.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
// nacos配置热刷新
//方式二：使用@ConfigurationProperties注解
// @RefreshScope
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

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


    /**
     * 路径： /user/110
     *
     * @param id 用户id
     * @return 用户
     */
    @RequestMapping("/redisTest/{id}")
    public User redisTest(@PathVariable("id") Long id) {
        List<Long> objects = new ArrayList<>();
        objects.add(1L);
        objects.add(2L);
        objects.add(3L);
       // redisUtil.lPush("push",objects);
       // redisUtil.hmSet("user",user);
        User dto = new User();
        dto.setAddress("测试111");
        dto.setUsername("测试222");
        redisUtil.hmset("user0",dtoTransMap(dto),10000);
        Map<Object, Object> user0 = redisUtil.hmget("user0");
        User user = mapTransToDto(user0);
        String jsonString = JSON.toJSONString(dto);
        redisUtil.set("user1", jsonString);
        Object user1 = redisUtil.get("user1");
        User parse = JSON.parseObject(String.valueOf(user1), User.class);


        return userService.queryById(id);
    }

    private User mapTransToDto(Map<Object, Object> user0){
        User dto = new User();
        Object username = user0.get("username");
        dto.setAddress(String.valueOf(username));
        dto.setUsername(String.valueOf(username));
        return dto;
    }

    private Map<String, Object> dtoTransMap(User dto){
        Map<String, Object> accountMap = new HashMap<>();
        accountMap.put("username", dto.getUsername());
        accountMap.put("address", dto.getAddress());
        return accountMap;
    }

}
