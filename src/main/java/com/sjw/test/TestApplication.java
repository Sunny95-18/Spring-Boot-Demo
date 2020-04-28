package com.sjw.test;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

//@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages ={"com.sjw.test.dao"})
@Slf4j
public class TestApplication implements CommandLineRunner {

//    @Autowired
//    private ApplicationContext appCtx;
//
//    @Autowired
//    private StringEncryptor codeSheepEncryptorBean;

    public static void main(String[] args) throws UnknownHostException {
//        String ip = InetAddress.getLocalHost().getHostAddress();
        SpringApplication.run(TestApplication.class, args);
        log.info("================================= success ==============================================");
    }

    @Override
    public void run(String... args) throws Exception {
//        Environment environment = appCtx.getBean(Environment.class);
//
//        // 首先获取配置文件里的原始明文信息
//        String mysqlOriginPswd = environment.getProperty("spring.datasource.password");
//        String redisOriginPswd = environment.getProperty("redis.password");

//        // 加密
//        String mysqlEncryptedPswd = encrypt( mysqlOriginPswd );
//        String redisEncryptedPswd = encrypt( redisOriginPswd );

        // 打印加密前后的结果对比
//        System.out.println( "MySQL原始明文密码为：" + mysqlOriginPswd );
//        System.out.println( "Redis原始明文密码为：" + redisOriginPswd );
//        System.out.println( "====================================" );
//        System.out.println( "MySQL原始明文密码加密后的结果为：" + mysqlEncryptedPswd );
//        System.out.println( "Redis原始明文密码加密后的结果为：" + redisEncryptedPswd );
    }
//    private String encrypt( String originPassord ) {
//        String encryptStr = codeSheepEncryptorBean.encrypt( originPassord );
//        return encryptStr;
//    }
//
//    private String decrypt( String encryptedPassword ) {
//        String decryptStr = codeSheepEncryptorBean.decrypt( encryptedPassword );
//        return decryptStr;
//    }
}
