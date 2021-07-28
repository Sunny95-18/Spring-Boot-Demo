package com.sjw.test.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;

/**
 * @author Sunny
 * @version 1.0
 * @date 2020/8/13 15:44
 */
@Configuration
public class PubConfig {

    /**
     * 订阅发布的主题
     * @return
     */
    @Bean
    ChannelTopic topic() {
        return new ChannelTopic( "pubsub:order" );
    }
}
