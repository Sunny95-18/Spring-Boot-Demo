package com.sjw.test.entity.user.vo;

import com.sjw.test.entity.user.User;
import lombok.Data;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/16 15:51
 */
@Data
public class UserTokenVo {

    private Integer id;
    private String username;
    private String token;
}
