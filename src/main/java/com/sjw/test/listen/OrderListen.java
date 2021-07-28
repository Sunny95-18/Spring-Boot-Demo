package com.sjw.test.listen;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/8/13 15:46
 */
@Component
public class OrderListen implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.out.println( "Message received: " + message.toString() );
    }
}
