package com.sjw.test.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/5/3 15:16
 */
@Component
@Slf4j
public class PlanScheduler {

    @Scheduled(cron = "*/5 * * * * ?")
    public void test1(){

        System.out.println("this is a test1");
        log.info("date:{}",new Date().toString());
    }
}
