package com.example.demo.consumer.concurrently;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author vincent
 * @description 测试 消费者
 * @Date 2020/4/16 4:53 下午
 */
public class TestConcurrently implements MessageListenerConcurrently {

    private static final Log log = LogFactory.get();

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt messageExt : list) {
            log.info("线程- {} 收到message 队列 - 消息id - 偏移({} - {} - {}) 来自 主题 - tag - key({} - {} - {})  消息主体：{}"
                ,Thread.currentThread().getName(),messageExt.getQueueId()
                ,messageExt.getMsgId(),messageExt.getQueueOffset()
                ,messageExt.getTopic(),messageExt.getTags()
                ,messageExt.getKeys(),new String(messageExt.getBody()));
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}
