package com.example.demo.consumer.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.demo.consumer.DefaultPushConsumer;
import com.example.demo.consumer.service.PushConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;

/**
 * @author vincent
 * @description 消费者 实现
 * @Date 2020/4/16 4:30 下午
 */
public class PushConsumerImpl extends DefaultPushConsumer implements PushConsumer {
    private static final Log log = LogFactory.get();
    DefaultMQPushConsumer consumer = super.getConsumer();

    MessageListenerConcurrently messageListenerConcurrently = null;

    public PushConsumerImpl(String NAME_SRV, String TOPIC, String TAG, String GROUP_NAME,MessageListenerConcurrently messageListenerConcurrently) {
        super(NAME_SRV, TOPIC, TAG, GROUP_NAME);
        this.messageListenerConcurrently = messageListenerConcurrently;
    }

    @Override
    public void start() {
        if (null != this.consumer){
            try {
                this.consumer.start();
                log.info("seoyu-info {} {} - {} 消费者 启动成功...",super.getGROUP_NAME(),super.getTOPIC(),super.getTAG());
            } catch (MQClientException e) {
                log.info("seoyu-error {} 消费者 启动 {} - {} 出错 ",super.getGROUP_NAME(),super.getTOPIC(),super.getTAG());
                log.error("seoyu-error 消费者启动 出错 ",e);
            }
        }
    }

    @Override
    public void shutdown() {
        if (null != this.consumer){
            this.consumer.shutdown();
            log.info("seoyu-info {} {} - {} 消费者 关闭成功...",super.getGROUP_NAME(),super.getTOPIC(),super.getTAG());
        }
    }

    @Override
    public void consumer() {
        this.consumer.registerMessageListener(this.messageListenerConcurrently);
    }

    @Override
    public void setBroadcasting() {
        super.setBroadcasting();
    }

    @Override
    public DefaultMQPushConsumer getConsumer() {
        return super.getConsumer();
    }
}
