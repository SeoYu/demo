package com.example.demo.consumer.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vincent
 * @description 消费者配置实体类
 * @Date 2020/4/16 5:14 下午
 */
@Component
@ConfigurationProperties(prefix = "mq.consumer")
public class ConsumerEntity {
    private List<String> topics = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private List<String> groups = new ArrayList<>();

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }
}
