package com.example.demo.producer;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.demo.producer.singleton.ProducerSingleton;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * @author vincent
 * @description 默认mq 生产者
 * @Date 2020/4/15 5:48 下午
 */
public class DefaultProducer {

    private static final Log log = LogFactory.get();

    private String NAME_SERVER;

    private String GROUP_NAME;

    private int RETRY_TIMES;

    private String INSTANCE_NAME;

    private DefaultMQProducer producer = null;

    public void start(){
        if (null != this.producer){
            try {
                this.producer.start();
                log.info("========= {}  producer 启动...",this.GROUP_NAME);
            } catch (MQClientException e) {
                log.error(e);
            }
        }
    }

    public void shutdown(){
        if (null != this.producer){
            this.producer.shutdown();
        }
    }

    public DefaultProducer(String NAME_SERVER, String GROUP_NAME, int RETRY_TIMES) {
        this.NAME_SERVER = NAME_SERVER;
        this.GROUP_NAME = GROUP_NAME;
        this.RETRY_TIMES = RETRY_TIMES;

        this.producer = ProducerSingleton.getSingleton(this.GROUP_NAME);
        this.producer.setRetryTimesWhenSendFailed(this.RETRY_TIMES);
        this.producer.setNamesrvAddr(this.NAME_SERVER);
    }

    public DefaultProducer(String NAME_SERVER, String GROUP_NAME, int RETRY_TIMES, String INSTANCE_NAME) {
        this.NAME_SERVER = NAME_SERVER;
        this.GROUP_NAME = GROUP_NAME;
        this.RETRY_TIMES = RETRY_TIMES;
        this.INSTANCE_NAME = INSTANCE_NAME;

        this.producer = ProducerSingleton.getSingleton(this.GROUP_NAME);
        this.producer.setRetryTimesWhenSendFailed(this.RETRY_TIMES);
        this.producer.setNamesrvAddr(this.NAME_SERVER);
        this.producer.setInstanceName(this.INSTANCE_NAME);
    }

    public DefaultMQProducer getProducer() {
        return this.producer;
    }
}
