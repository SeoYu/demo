package com.example.demo.producer.singleton;

import org.apache.rocketmq.client.producer.DefaultMQProducer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author vincent
 * @description 生产者集合 单例
 * @Date 2020/4/16 2:31 下午
 */
public class ProducerSingleton {
    private volatile static ConcurrentHashMap<String, DefaultMQProducer> map = new ConcurrentHashMap<>(8);

    public static DefaultMQProducer getSingleton(String group_name){
        if (!map.contains(group_name)){
            synchronized (ProducerSingleton.class) {
                if (!map.contains(group_name)) {
                    map.put(group_name,new DefaultMQProducer(group_name));
                }
            }
        }
        return map.get(group_name);
    }
}
