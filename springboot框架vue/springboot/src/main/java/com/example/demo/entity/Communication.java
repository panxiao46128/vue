package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("communication")
@Data
public class Communication {

    private Integer id;
    private String address;
    private String content;
    private String name;

}
