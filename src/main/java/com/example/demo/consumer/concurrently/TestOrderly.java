package com.example.demo.consumer.concurrently;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author vincent
 * @description 测试 顺序消费
 * @Date 2020/4/16 6:27 下午
 */
public class TestOrderly implements MessageListenerOrderly {

    private static final Log log = LogFactory.get();

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
        consumeOrderlyContext.setAutoCommit(true);
        for (MessageExt messageExt : list) {
            log.info("线程- {} 收到message 队列 - 消息id - 偏移({} - {} - {}) 来自 主题 - tag - key({} - {} - {})  消息主体：{}"
                ,Thread.currentThread().getName(),messageExt.getQueueId()
                ,messageExt.getMsgId(),messageExt.getQueueOffset()
                ,messageExt.getTopic(),messageExt.getTags()
                ,messageExt.getKeys(),new String(messageExt.getBody()));
        }
        return ConsumeOrderlyStatus.SUCCESS;
    }
}
