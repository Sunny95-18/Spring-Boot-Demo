package com.sjw.test.service.order;

import com.sjw.test.common.config.MsgPublisher;
import com.sjw.test.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/8/13 15:37
 */
@Service
@Slf4j
public class OrderServiceImpl implements  OrderService{


    public static  int number=10;

    @Autowired
    private MsgPublisher msgPublisher;

    @Override
    public boolean createOrder() {
        if(number<0){
            log.error("无需请求");
            return false;
        }
        if(number==0){
            log.warn("已结束");
            msgPublisher.sendMsg("number已经使用完");
            number--;
            return false;
        }
        number--;
        log.info("当前数量：{}",number);
        return true;
    }
}
