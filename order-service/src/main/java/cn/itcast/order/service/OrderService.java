package cn.itcast.order.service;

import cn.itcast.order.pojo.Order;

import java.util.List;

public interface OrderService {

    public Order queryOrderById2(Long orderId);
    public Order queryOrderById(Long orderId);
    public List<Order> batchInsert();
}
