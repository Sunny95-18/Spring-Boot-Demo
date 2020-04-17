package com.sjw.test.entity.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/16 15:49
 */
@Data
public class UserLoginDto implements Serializable {

    @NotNull
    @ApiModelProperty("用户名")
    private String username;
    @NotNull
    @ApiModelProperty("密码")
    private String password;
}
