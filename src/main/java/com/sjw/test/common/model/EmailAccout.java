package com.sjw.test.common.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 * Created by Sunny on 2019/6/27 11:22
 */
@Getter
@Setter
public class EmailAccout {
    // 邮箱用户
    private String username;

    // 邮箱密码
    private String password;

    // 邮箱服务器
    private String place;
}
