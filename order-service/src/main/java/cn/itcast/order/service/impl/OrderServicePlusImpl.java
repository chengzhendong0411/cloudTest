package cn.itcast.order.service.impl;

import cn.itcast.order.mapper.OrderPlusMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.service.OrderServicePlus;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderServicePlusImpl extends ServiceImpl<OrderPlusMapper,Order> implements OrderServicePlus {
}
