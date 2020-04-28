package com.example.demo.consumer.service;

import com.sun.tools.javadoc.Start;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

/**
 * @author vincent
 * @description 基础类
 * @Date 2020/4/16 3:15 下午
 */
public interface PushConsumer {

    public void start();
    public void shutdown();
    public void consumer();
    public void setBroadcasting();
    public DefaultMQPushConsumer getConsumer();

}
