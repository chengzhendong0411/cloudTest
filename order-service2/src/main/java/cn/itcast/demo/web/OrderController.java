package cn.itcast.demo.web;

import cn.itcast.demo.pojo.Order;
import cn.itcast.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

   @Autowired
   private OrderService orderService;

    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {

        // 根据id查询订单并返回
        Order order = orderService.queryOrderById(orderId);
        System.out.println("热部署成功");
        return order;
    }
}
