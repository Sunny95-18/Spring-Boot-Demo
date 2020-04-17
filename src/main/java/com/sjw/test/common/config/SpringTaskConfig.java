package com.sjw.test.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

/**
 *  定时任务配置
 * @author Sunny
 * @version 1.0
 * @date 2020/4/17 13:47
 */
@Configuration
@EnableScheduling
public class SpringTaskConfig {

    //开启多线程模式
    @Bean
    public TaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler scheduler=new ThreadPoolTaskScheduler();
        //线程池大小
        scheduler.setPoolSize(10);
        //线城池前缀
        scheduler.setThreadNamePrefix("spring-task-thread");
        return scheduler;
    }
}
