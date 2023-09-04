package cn.itcast.order.mapper;

import cn.itcast.order.pojo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderPlusMapper extends BaseMapper<Order> {
}
