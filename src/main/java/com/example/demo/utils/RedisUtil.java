package com.example.demo.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author vincent
 * @description redis
 * @Date 2020-04-09 15:49
 */
@Component
public class RedisUtil {

    private static final Log log = LogFactory.get();

    @Autowired
    private static StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final long EXPIRTIME = 60l; // 默认生存时间60秒

    /**
     * String 类型
     */

    /**
     * set
     *
     * @param key
     * @param value
     * @param time  单位秒
     */
    public void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, EXPIRTIME, TimeUnit.SECONDS);
    }

    /**
     * map key-value 保存
     * 没有默认过期时间 默认为-1永久 慎用
     * @param map
     */
    public void multiSet(Map<String, Object> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 判断当前key是否存在，不存在不操作，存在判断val是不是当前value，不是则更新
     *
     * @param key
     * @param value
     * @param time
     */
    public boolean setIfPresent(String key, Object value, long time) {
        return redisTemplate.opsForValue().setIfPresent(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 判断当前key是否存在，不存在不操作，存在判断val是不是当前value，不是则更新
     *
     * @param key
     * @param value
     */
    public boolean setIfPresent(String key, Object value) {
        return redisTemplate.opsForValue().setIfPresent(key, value, EXPIRTIME, TimeUnit.SECONDS);
    }

    /**
     * 判断当前key是否存在，不存在则新增，存在判断val是不是当前value，不是则更新
     *
     * @param key
     * @param value
     * @param time
     */
    public boolean setIfAbsent(String key, Object value, long time) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 判断当前key是否存在，不存在则新增，存在判断val是不是当前value，不是则更新
     *
     * @param key
     * @param value
     */
    public boolean setIfAbsent(String key, Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, EXPIRTIME, TimeUnit.SECONDS);
    }


    /**
     * get
     *
     * @param key
     * @return String
     */
    public String getStr(String key) {
        return Convert.toStr(redisTemplate.opsForValue().get(key));
    }

    public int getInt(String key) {
        return Convert.toInt(redisTemplate.opsForValue().get(key));
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Object getAndSet(String key, Object value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    public List<Object> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 确保value为数字类型 增1
     * @param key
     * @return
     */
    public long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 减1
     * @param key
     * @return
     */
    public long decrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * Hash
     */

    /**
     * put
     * @param key map名
     * @param hashKey 键名
     * @param value 值
     */
    public void setHash(String key,String hashKey,Object value){
        redisTemplate.opsForHash().put(key,hashKey,value);
        expire(key,EXPIRTIME);
    }
    public void setHash(String key,String hashKey,Object value,long time){
        redisTemplate.opsForHash().put(key,hashKey,value);
        expire(key,time);
    }
    public void setHash(String key,Map<String,Object> map){
        redisTemplate.opsForHash().putAll(key,map);
        expire(key,EXPIRTIME);
    }
    public void setHash(String key,Map<String,Object> map,long time){
        redisTemplate.opsForHash().putAll(key,map);
        expire(key,time);
    }

    /**
     * hash结构 hashkey判断
     * @param key
     * @param hashKey
     * @return
     */
    public boolean hasKey(String key,String hashKey){
        return redisTemplate.opsForHash().hasKey(key,hashKey);
    }

    public Object getHash(String key, String hashKey){
        return redisTemplate.opsForHash().get(key,hashKey);
    }

    public List<Object> valuesHash(String key){
        return redisTemplate.opsForHash().values(key);
    }

    public List<Object> multiGetHash(String key, Collection<Object> keys){
        return redisTemplate.opsForHash().multiGet(key,keys);
    }

    public Set<Object> keysHash(String key){
        return redisTemplate.opsForHash().keys(key);
    }

    public Map<Object, Object> entriesHash(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    public HashMap<Object, Object> scanHash(String key, String hashKey){
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, ScanOptions.scanOptions().match(hashKey).build());
        HashMap<Object, Object> map = new HashMap<>();
        while (cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            map.put(entry.getKey(),entry.getValue());
        }
        return map;
    }

    public long deleteHash(String key, String... keys){
        return redisTemplate.opsForHash().delete(key,keys);
    }

    /**
     * list
     * @param key
     * @return
     */


    /**
     * 在指定位置插入
     * @param key
     * @param value
     * @param index
     */
    public void setIndex(String key,Object value,long index){
        redisTemplate.opsForList().set(key,index,value);
        expire(key,EXPIRTIME);
    }
    public void setIndex(String key,Object value,long index,long time){
        redisTemplate.opsForList().set(key,index,value);
        expire(key,time);
    }

    /**
     * 头部添加
     * @param key
     * @param value
     */
    public void leftPush(String key,Object value){
        redisTemplate.opsForList().leftPush(key,value);
        expire(key,EXPIRTIME);
    }
    public void leftPush(String key,Object value,long time){
        redisTemplate.opsForList().leftPush(key,value);
        expire(key,time);
    }
    public void leftPushAll(String key,Object... values){
        redisTemplate.opsForList().leftPushAll(key,values);
        expire(key,EXPIRTIME);
    }
    public void leftPushAll(String key,long time,Object... values){
        redisTemplate.opsForList().leftPushAll(key,values);
        expire(key,time);
    }
    public void leftPushAll(String key,Collection<Object> values){
        redisTemplate.opsForList().leftPushAll(key,values);
        expire(key,EXPIRTIME);
    }
    public void leftPushAll(String key,long time,Collection<Object> values){
        redisTemplate.opsForList().leftPushAll(key,values);
        expire(key,time);
    }

    /**
     * 尾部追加
     * @param key
     * @param value
     */
    public void rightPush(String key,Object value){
        redisTemplate.opsForList().rightPush(key,value);
        expire(key,EXPIRTIME);
    }
    public void rightPush(String key,Object value,long time){
        redisTemplate.opsForList().rightPush(key,value);
        expire(key,time);
    }
    public void rightPushAll(String key,Object... values){
        redisTemplate.opsForList().rightPushAll(key,values);
        expire(key,EXPIRTIME);
    }
    public void rightPushAll(String key,long time,Object... values){
        redisTemplate.opsForList().rightPushAll(key,values);
        expire(key,time);
    }
    public void rightPushAll(String key,Collection<Object> values){
        redisTemplate.opsForList().rightPushAll(key,values);
        expire(key,EXPIRTIME);
    }
    public void rightPushAll(String key,long time,Collection<Object> values){
        redisTemplate.opsForList().rightPushAll(key,values);
        expire(key,time);
    }

    /**
     * 获取指定index的value
     * @param key
     * @param index
     * @return
     */
    public Object indexOfList(String key, long index){
        return redisTemplate.opsForList().index(key,index);
    }

    public List<Object> range(String key, long start, long end){
        return redisTemplate.opsForList().range(key,start,end);
    }

    public Object leftPop(String key){
        return redisTemplate.opsForList().leftPop(key);
    }

    public Object rightPop(String key){
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移除
     * @param key
     * @param value
     * @param tag   >0 头-尾第一个 <0 尾-头第一个 =0 全部
     * @return
     */
    public long remove(String key, Object value, long tag){
        return redisTemplate.opsForList().remove(key,tag,value);
    }

    /**
     * set
     * @return
     */

    public void set(){
//        redisTemplate.opsForZSet().;
    }






    public long getExpire(String key){
        return redisTemplate.getExpire(key);
    }

    public boolean expire(String key, long time){
        return redisTemplate.expire(key,time,TimeUnit.SECONDS);
    }

}
