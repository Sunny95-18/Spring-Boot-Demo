package com.sjw.test.entity.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/21 16:53
 */
@Data
@TableName("log")
public class Log extends Model<Log> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date ctime;

    private String username;

    private String content;

    @TableField("api_uri")
    private String apiUri;

    @TableField("api_name")
    private String apiName;

    private Long code;

    @TableField("code_name")
    private String codeName;
}
