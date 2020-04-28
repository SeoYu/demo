package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.constant.EsAnalyzerEnum;
import com.example.demo.constant.EsTypeEnum;
import com.example.demo.producer.send.ProducerSend;
import com.example.demo.utils.EsQuery;
import com.example.demo.utils.EsUtil;
import com.example.demo.utils.RedisUtil;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    EsUtil esUtil;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ProducerSend testProducer;
    @Autowired
    ProducerSend test2Producer;

    @Test
    public void contextLoads() throws InterruptedException {
//        Map map = new HashMap<>();
//        map.put("score",0);
//        redisUtil.multiSet(map);
        String key = "score";
        redisUtil.set("score",0);
        System.out.println(redisUtil.get("score"));
//        System.out.println(redisUtil.getExpire("score"));
        redisUtil.increment(key);
//        Thread.sleep(11000);
        System.out.println(redisUtil.get("score"));
        redisUtil.decrement(key);
        System.out.println(redisUtil.get("score"));
    }

    @Test
    public void send(){
        try {
//            SendResult helloWorld = testProducer.send("1", "hello world");
            SendResult helloWorld = test2Producer.send("1", "hello world");
            System.out.println(JSON.toJSONString(helloWorld));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
        System.out.println(new EsQuery().setColumn("desc", EsTypeEnum.TEXT, EsAnalyzerEnum.IK_MAX_WORD).getString());
    }

    @Test
    public void listHistory() {
        try {
            esUtil.listHistory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

