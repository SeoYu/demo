package com.example.demo.producer.entity;

import org.aspectj.weaver.ast.Var;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vincent
 * @description 生产者 参数实体
 * @Date 2020/4/17 5:59 下午
 */
@Component
@ConfigurationProperties(prefix = "mq.producer")
public class ProducerEntity {
    private List<String> groups = new ArrayList<>();
    private List<String> topics = new ArrayList<>();

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
}
