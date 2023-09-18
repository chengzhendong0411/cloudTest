package cn.itcast.order.mapper;

import cn.itcast.order.pojo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderPlusMapper extends BaseMapper<Order> {

    Integer batchInsert(List<Order> orders);

    Integer batchUpdate(List<Order> orders);
}
