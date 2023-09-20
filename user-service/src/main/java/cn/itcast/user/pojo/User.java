package cn.itcast.user.pojo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Long id;
    private String username;
    private String address;
    private String createTime;
    private String price;
}