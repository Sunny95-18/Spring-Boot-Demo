package com.sjw.test.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sjw.test.common.vo.Response;
import com.sjw.test.entity.user.User;
import com.sjw.test.entity.user.dto.UserLoginDto;
import com.sjw.test.entity.user.vo.UserTokenVo;

import java.util.List;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/13 14:59
 */
public interface UserService extends IService<User> {

    List<User> seleceUsers();

    boolean updateUser(String name);

    User getUserByUserName(String username);


    UserTokenVo login(UserLoginDto userLoginDto);


    Boolean register(UserLoginDto dto);
}
