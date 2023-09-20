package cn.itcast.order.pojo;

import cn.itcast.demo.pojo.User;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
// @TableName("tb_order")
public class Order implements Serializable {
    //@TableId
    private Long id;
    private Long price;
    private String name;
    private Integer num;
    private Long userId;
   // @TableField(value = "createTime")
    private Date createTime;
   // private User user;
}