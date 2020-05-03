package com.sjw.test.job;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/4/17 13:49
 */
@Slf4j
//@Component
public class TestTask {

//    @Scheduled(cron = "0 0/1 * ? * ?")
//    @Scheduled(fixedRate = 10000)
    private void cancelTimeOutOrder() {
        log.info("这儿是个定时任务：{}",new Date());
    }

}
