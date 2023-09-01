package cn.itcast.demo.service;

import cn.itcast.demo.mapper.OrderMapper;
import cn.itcast.demo.pojo.Order;
import cn.itcast.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate restTemplate;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 利用RestTemplate发送http请求，查询用户（硬编码方式）
        // eureka 可以基于服务名称拉去到服务列表
        String url = "http://userservice/user/"+order.getUserId();
        User forObject = restTemplate.getForObject(url, User.class);
        order.setUser(forObject);
        return order;
    }
}
