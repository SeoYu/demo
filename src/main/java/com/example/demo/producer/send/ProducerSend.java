package com.example.demo.producer.send;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSON;
import com.example.demo.producer.DefaultProducer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

/**
 * @author vincent
 * @description 生产者发送方法 方法集
 * @Date 2020/4/16 2:51 下午
 */
public class ProducerSend {

    private static final Log log = LogFactory.get();

    private String topic;

    private DefaultMQProducer producer;
    
    private static final String DEFAULTTAG = "*";

    public ProducerSend(String topic, DefaultProducer defaultProducer) {
        this.topic = topic;
        this.producer = defaultProducer.getProducer();
    }

    public SendResult sendTopic(String topic, String tag, String key, Object msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = getMessageWithT(topic, tag, key, msg);
        SendResult sendResult = this.producer.send(message);
        return sendResult;
    }

    public SendResult sendTopic(String topic,String key,Object msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = getMessageWithT(topic, DEFAULTTAG, key, msg);
        SendResult sendResult = this.producer.send(message);
        return sendResult;
    }

    public SendResult send(String tag, String key, Object msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = getMessage(tag, key, msg);
        SendResult sendResult = this.producer.send(message);
        return sendResult;
    }

    public SendResult send(String key,Object msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = getMessage(DEFAULTTAG, key, msg);
        SendResult sendResult = this.producer.send(message);
        return sendResult;
    }
    
    public void sendAsync(String tag,String key,Object msg) throws RemotingException, MQClientException, InterruptedException {
        Message message = getMessage(tag, key, msg);
        this.producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("seoyu-info 异步发送成功 topic - tag - key ({} - {} - {})",topic,tag,key);
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("seoyu-error 异步发送失败 topic - tag - key ({} - {} - {})",topic,tag,key);
                log.error("seoyu-error 异步发送失败",throwable);
            }
        });
    }

    public void sendAsync(String key,Object msg) throws RemotingException, MQClientException, InterruptedException {
        Message message = getMessage(DEFAULTTAG, key, msg);
        this.producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("seoyu-info 异步发送成功 topic - tag - key ({} - {} - {})",topic,DEFAULTTAG,key);
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("seoyu-error 异步发送失败 topic - tag - key ({} - {} - {})",topic,DEFAULTTAG,key);
                log.error("seoyu-error 异步发送失败",throwable);
            }
        });
    }

    public void sendOneWay(String tag,String key,Object msg) throws RemotingException, MQClientException, InterruptedException {
        Message message = getMessage(tag, key, msg);
        this.producer.sendOneway(message);
    }

    public void sendOneWay(String key,Object msg) throws RemotingException, MQClientException, InterruptedException {
        Message message = getMessage(DEFAULTTAG, key, msg);
        this.producer.sendOneway(message);
    }

    public void sendOrder(String tag,String key,Object msg,Object queue_id) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = getMessage(tag, key, msg);
        this.producer.send(message, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                return null;
            }
        },queue_id);
    }




    private Message getMessage(String tag, String key, Object msg) {
        return new Message(this.topic,tag,key, JSON.toJSONString(msg).getBytes());
    }

    private Message getMessageWithT(String topic, String tag, String key, Object msg) {
        return new Message(topic, tag, key, JSON.toJSONString(msg).getBytes());
    }
}
