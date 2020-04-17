package com.sjw.test;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages ={"com.sjw.test.dao"})
@Slf4j
public class TestApplication {

    public static void main(String[] args) {

        SpringApplication.run(TestApplication.class, args);
        log.info("================================= success ==============================================");
    }

}
