package cn.itcast.order.service;

import cn.itcast.demo.clients.UserClient;
import cn.itcast.demo.pojo.User;
import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserClient userClient;

    // 通过 Feign 远程调用
    public Order queryOrderById2(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 远程调用
        User user = userClient.findById(orderId);
        //order.setUser(user);
        return order;
    }

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 利用RestTemplate发送http请求，查询用户（硬编码方式）
        //String url = "http://localhost:8081/user/"+order.getUserId();
        String url = "http://userservice/user/"+order.getUserId();
        User forObject = restTemplate.getForObject(url, User.class);
        //order.setUser(forObject);
        return order;
    }

    // 以下是为了测试 mybatisplus

    /**
     *  通过mybatisPlus进行表的查询
     * @return
     */
    public Order queryOrderInfoByMybatisPlus(){







        return null;
    }



}
