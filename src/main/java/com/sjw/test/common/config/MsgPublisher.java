package com.sjw.test.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;

@Component
@Slf4j
public class MsgPublisher {



    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private ChannelTopic topic;

    public void sendMsg(String msg){
        redisTemplate.convertAndSend( topic.getTopic(), "Message: " + msg +
                ";Time:" + Calendar.getInstance().getTime());
    }
}