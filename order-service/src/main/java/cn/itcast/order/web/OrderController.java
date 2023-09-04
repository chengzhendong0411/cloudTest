package cn.itcast.order.web;

import cn.itcast.order.pojo.Order;
import cn.itcast.order.service.OrderService;
import cn.itcast.order.service.OrderServicePlus;
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
   @Autowired
   private OrderServicePlus orderServicePlus;

    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {

        // 根据id查询订单并返回
        Order order = orderService.queryOrderById(orderId);
        System.out.println("热部署成功");
        return order;
    }
    @GetMapping("/feign/{orderId}")
    public Order queryOrderById2(@PathVariable("orderId") Long orderId) {

        // 根据id查询订单并返回
        Order order = orderService.queryOrderById2(orderId);
        System.out.println("热部署成功");
        return order;
    }
    // mybatisPlus测试

    @GetMapping("/queryOrderPlus/{orderId}")
    public Order queryOrderPlus(@PathVariable("orderId") Long orderId) {
        // 根据id查询订单并返回
        Order order = orderServicePlus.getById(orderId);
        System.out.println("热部署成功 "+order);
        return order;
    }

}
