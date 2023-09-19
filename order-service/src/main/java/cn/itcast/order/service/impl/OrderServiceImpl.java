package cn.itcast.order.service.impl;

import cn.itcast.demo.clients.UserClient;
import cn.itcast.demo.pojo.User;
import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.mapper.OrderPlusMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.service.OrderService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrderPlusMapper orderPlusMapper;

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


    /**
     *  批量插入
     * @return
     */
    public List<Order> batchInsert(){
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Order build = Order.builder().num(i).name("这是测试名字"+i).price(Long.valueOf(i)).userId(Long.valueOf(1)).build();
            orders.add(build);
        }
        Integer num = orderPlusMapper.batchInsert(orders);
        orders.stream().forEach(dto->{dto.setName("批量修改了！！！");dto.setId(1L);});
        orderPlusMapper.batchUpdate(orders);
        return null;
    }

    /**
     *  批量插入
     * @return
     */
    public List<Order> pageTest(){
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Order build = Order.builder().num(i).name("这是测试名字"+i).price(Long.valueOf(i)).userId(Long.valueOf(1)).build();
            orders.add(build);
        }
        /*int total = 1000;
        int currpage = 1;
        int pageSize = 100;
        int n = total % pageSize;
        while (currpage < n){
             currpage = currpage + 1;
        }*/




        return null;
    }



}
