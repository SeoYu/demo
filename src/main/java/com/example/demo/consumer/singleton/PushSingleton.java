package com.example.demo.consumer.singleton;

import com.example.demo.consumer.DefaultPushConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author vincent
 * @description push类消费单例
 * @Date 2020/4/16 5:38 下午
 */
public class PushSingleton {
    private volatile static ConcurrentHashMap<String, DefaultMQPushConsumer> map = new ConcurrentHashMap<>(8);

    public static DefaultMQPushConsumer getSingleton(String group_name){
        if (!map.contains(group_name)){
            synchronized (PushSingleton.class) {
                if (!map.contains(group_name)) {
                    map.put(group_name,new DefaultMQPushConsumer(group_name));
                }
            }
        }
        return map.get(group_name);
    }
}
