package com.example.demo.consumer;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.demo.consumer.singleton.PushSingleton;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * @author vincent
 * @description 父类
 * @Date 2020/4/16 4:32 下午
 */
public class DefaultPushConsumer {
    private static final Log log = LogFactory.get();

    private String NAME_SRV;
    private String TOPIC;
    private String TAG;
    private String GROUP_NAME;
    private DefaultMQPushConsumer consumer;

    public String getNAME_SRV() {
        return NAME_SRV;
    }

    public void setNAME_SRV(String NAME_SRV) {
        this.NAME_SRV = NAME_SRV;
    }

    public String getTOPIC() {
        return TOPIC;
    }

    public void setTOPIC(String TOPIC) {
        this.TOPIC = TOPIC;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public String getGROUP_NAME() {
        return GROUP_NAME;
    }

    public void setGROUP_NAME(String GROUP_NAME) {
        this.GROUP_NAME = GROUP_NAME;
    }

    public DefaultMQPushConsumer getConsumer() {
        return consumer;
    }

    public DefaultPushConsumer(String NAME_SRV, String TOPIC, String TAG, String GROUP_NAME) {
        this.NAME_SRV = NAME_SRV;
        this.TOPIC = TOPIC;
        this.TAG = TAG;
        this.GROUP_NAME = GROUP_NAME;

        this.consumer = PushSingleton.getSingleton(this.GROUP_NAME);
        this.consumer.setNamesrvAddr(this.NAME_SRV);
        this.consumer.setMessageModel(MessageModel.CLUSTERING);
        this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        try {
            this.consumer.subscribe(this.TOPIC,TAG);
        } catch (MQClientException e) {
            log.info("seoyu-error {} 消费者 订阅 {} - {} 出错 ",this.GROUP_NAME,this.TOPIC,this.TAG);
            log.error("seoyu-error 消费者订阅 出错 ",e);
        }
    }

    public void setBroadcasting(){
        this.consumer.setMessageModel(MessageModel.BROADCASTING);
    }
}
