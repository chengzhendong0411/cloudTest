package cn.itcast.order.web;

import cn.itcast.order.pojo.Order;
import cn.itcast.order.service.OrderService;
import cn.itcast.order.service.impl.OrderServiceImpl;
import cn.itcast.order.service.OrderServicePlus;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/order")
public class OrderController {

   @Autowired
   private OrderServiceImpl orderServiceImpl;
   @Autowired
   private OrderServicePlus orderServicePlus;
   @Autowired
   private OrderService orderService;
   @Resource
   private RedissonClient redissonClient;

    @GetMapping("/{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {

        // 根据id查询订单并返回
        Order order = orderServiceImpl.queryOrderById(orderId);
        System.out.println("热部署成功");
        return order;
    }
    @GetMapping("/feign/{orderId}")
    public Order queryOrderById2(@PathVariable("orderId") Long orderId) {

        // 根据id查询订单并返回
        Order order = orderServiceImpl.queryOrderById2(orderId);
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

    /**
     * 批量插入 通过注解形式
     * @return
     */
    @GetMapping("/batchInsert")
    public Order batchInsert() {
        orderService.batchInsert();
        return null;
    }



    /**
     * 批量插入 通过注解形式
     * @return
     */
    @GetMapping("/redisson")
    public Order redisson() {
        RLock lock = redissonClient.getLock("test");
        try {
            boolean b = lock.tryLock(0, 60, TimeUnit.SECONDS);
            if(b){
                System.out.println("获取到了锁");
                // string类型
                RBucket<Object> bucket = redissonClient.getBucket("key");
                bucket.set("value",100,TimeUnit.SECONDS);
                String o =String.valueOf(bucket.get());
                System.out.println("String "+o);

                Order order = new Order();
                order.setId(1L);
                order.setName("test");
                RBucket<Object> order1 = redissonClient.getBucket("order");
                order1.set(order,100,TimeUnit.SECONDS);
                Object o2 = order1.get();
                Order order2 = JSON.parseObject(JSON.toJSONString(o2), Order.class);
                System.out.println(order2);

                // List
                List<Object> objects = new ArrayList<>();
                objects.add(1);
                objects.add(2);
                objects.add(3);
                RList<Object> list = redissonClient.getList("list");
                list.addAll(objects);
                list.add("tom");
                list.add("king");
                list.expire(500,TimeUnit.SECONDS);
                Object o1 = list.get(1);
                RList<Object> list1 = redissonClient.getList("list");

                System.out.println("list   "+o1+"  test   "+list1);
                // Map
                RMap<Object, Object> map = redissonClient.getMap("map");
                map.put("1",objects);
                map.put("2",order);
                map.expire(500,TimeUnit.SECONDS);
                Object map1 = map.get("1");
                Object map2 = map.get("2");
                Order order3 = JSON.parseObject(JSON.toJSONString(map2), Order.class);
                System.out.println("map  :"+map1 +"    "+map2+"   "+order3);
                // set
                RSet<Object> test = redissonClient.getSet("set");
                test.add(1);
                test.add(2);
                test.expire(500,TimeUnit.SECONDS);
                RSet<Object> set = redissonClient.getSet("set");
                System.out.println(set);
                redissonClient.getKeys().delete("key");

            }else{
                System.out.println("没有获取到锁");
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }finally {
            if (lock != null && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return null;
    }




}
