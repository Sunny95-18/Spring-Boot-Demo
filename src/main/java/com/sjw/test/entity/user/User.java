package com.sjw.test.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/13 14:51
 */
@Data
@TableName("user")
public class User extends Model<User> {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String password;
}
