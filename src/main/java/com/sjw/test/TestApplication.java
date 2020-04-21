package com.sjw.test;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

import java.net.InetAddress;
import java.net.UnknownHostException;

//@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages ={"com.sjw.test.dao"})
@Slf4j
public class TestApplication {

    public static void main(String[] args) throws UnknownHostException {
        String ip = InetAddress.getLocalHost().getHostAddress();

        SpringApplication.run(TestApplication.class, args);
        log.info("================================= success ==============================================");
        System.out.println(ip);
    }

}
