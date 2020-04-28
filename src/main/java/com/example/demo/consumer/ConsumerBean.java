package com.example.demo.consumer;

import com.example.demo.consumer.concurrently.TestConcurrently;
import com.example.demo.consumer.entity.ConsumerEntity;
import com.example.demo.consumer.service.impl.PushConsumerImpl;
import com.example.demo.consumer.service.PushConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author vincent
 * @description 消费者bean集合
 * @Date 2020/4/16 3:13 下午
 */
@Configuration
public class ConsumerBean extends CachingConfigurerSupport {

    @Autowired
    private Environment env;

    @Value("${mq.name-server}")
    private String NAMESERVER;

    @Autowired
    ConsumerEntity consumerEntity;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public PushConsumer pushConsumer() {
        PushConsumerImpl pushConsumer = new PushConsumerImpl(
            NAMESERVER
            , consumerEntity.getTopics().get(0)
            , consumerEntity.getTags().get(0)
            , consumerEntity.getGroups().get(0)
            , new TestConcurrently());
        pushConsumer.consumer();
        return pushConsumer;
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public PushConsumer push2Consumer() {
        PushConsumerImpl pushConsumer = new PushConsumerImpl(
            NAMESERVER
            , consumerEntity.getTopics().get(1)
            , consumerEntity.getTags().get(1)
            , consumerEntity.getGroups().get(1)
            , new TestConcurrently());
//        pushConsumer.setBroadcasting(); // 设置广播消费
        pushConsumer.consumer();
        return pushConsumer;
    }

}
