package com.sjw.test;

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
    public void testLogin() throws Exception{
        Process process= Runtime.getRuntime().exec("notepad.exe");
       int rv= process.waitFor();
       System.out.println("rv:"+rv);
    }
}
