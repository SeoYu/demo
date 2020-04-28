package com.example.demo.producer;

import com.example.demo.producer.entity.ProducerEntity;
import com.example.demo.producer.send.ProducerSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author vincent
 * @description 生产者 bean集合
 * @Date 2020/4/16 11:12 上午
 */
@Configuration
public class ProducerBean extends CachingConfigurerSupport {

    @Autowired
    private Environment env;

    @Value("${mq.name-server}")
    private String NAMESERVER;

    @Value("${mq.retry-times}")
    private int RETRY_TIMES;

    @Value("${mq.instance-name}")
    private String INSTANCE_NAME;

    @Autowired
    ProducerEntity producerEntity;

    @Bean(initMethod = "start",destroyMethod = "shutdown")
    public DefaultProducer myProducer(){
        DefaultProducer defaultProducer = new DefaultProducer(NAMESERVER,producerEntity.getGroups().get(0),RETRY_TIMES);
        return defaultProducer;
    }

    @Bean
    public ProducerSend test2Producer(DefaultProducer defaultProducer){
        ProducerSend producerSend = new ProducerSend(producerEntity.getTopics().get(0), defaultProducer);
        return producerSend;
    }

    @Bean
    public ProducerSend testProducer(DefaultProducer defaultProducer){
        ProducerSend producerSend = new ProducerSend(producerEntity.getTopics().get(1), defaultProducer);
        return producerSend;
    }

}
