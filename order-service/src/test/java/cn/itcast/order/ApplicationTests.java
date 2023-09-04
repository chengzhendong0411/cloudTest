package cn.itcast.order;

import cn.itcast.order.mapper.OrderPlusMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.service.OrderService;
import cn.itcast.order.service.OrderServicePlus;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// 测试类
@SpringBootTest()
class ApplicationTests {

    @Autowired
    private OrderPlusMapper orderPlusMapper;

    @Autowired
    private OrderServicePlus orderServicePlus;

    @Test
    public void testMybatisPlus(){
        System.out.println("测试 ");
        Order order1 = orderServicePlus.getById(101);
        System.out.println("查询order "+order1);
        order1.setId(1001L);
        order1.setName("plus插入1");
        orderServicePlus.save(order1);

        order1.setId(101L);
        /*QueryWrapper<Order> wrapper = new QueryWrapper();
        wrapper.eq("id",10L);
        orderServicePlus.getById(wrapper);*/
    }


}
