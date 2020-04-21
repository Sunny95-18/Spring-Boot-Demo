package com.sjw.test;

import com.sjw.test.entity.user.dto.UserLoginDto;
import com.sjw.test.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestApplicationTests {


    @Autowired
    private UserService userService;
    @Test
    void contextLoads() {
    }

    @Test
    public void testLogin(){
        UserLoginDto userLoginDto=new UserLoginDto();
        userLoginDto.setUsername("sunny");
        userLoginDto.setPassword("123456");
        userService.login(userLoginDto);
    }
}
