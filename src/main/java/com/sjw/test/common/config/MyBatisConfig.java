package com.sjw.test.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 *
 */
@Configuration
@MapperScan({"com.sjw.test.dao"})
public class MyBatisConfig {
}
