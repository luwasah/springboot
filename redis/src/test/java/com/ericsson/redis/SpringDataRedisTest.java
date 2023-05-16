package com.ericsson.redis;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 操作String类型数据
     */
    @Test
    public void testString() throws InterruptedException {
        //存值
        redisTemplate.opsForValue().set("name", "tom");
        //取值
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println("---------name: " + name);

        //存值同时设置过期时间
        redisTemplate.opsForValue().set("age", "20", 5l, TimeUnit.SECONDS);
        Object age = redisTemplate.opsForValue().get("age");
        System.out.println("---------age: " + age);
        Thread.sleep(6000);
        age = redisTemplate.opsForValue().get("age");
        System.out.println("---------age: " + age);
    }

    /**
     * 操作Hash类型数据
     */
    @Test
    public void testHash(){
        HashOperations hashOps = redisTemplate.opsForHash();
        //存执
        hashOps.put("001", "name", "tom");
        hashOps.put("001", "age", "20");
        hashOps.put("001", "gender", "man");
        //取值
        Object name = hashOps.get("001", "name");
        System.out.println("---------name: " + name);
        //获取指定hash的所有key
        Set keys = hashOps.keys("001");
        keys.forEach(key -> System.out.println(key));
        //获取指定hash的所有value
        List values = hashOps.values("001");
        values.forEach(value -> System.out.println(value));
        //获取指定hash的所有entry
        Map entries = hashOps.entries("001");
        System.out.println("entries: " + JSON.toJSONString(entries));
    }

    /**
     * 操作List类型数据
     */
    @Test
    public void testList(){
        ListOperations listOps = redisTemplate.opsForList();
        //存执
        listOps.leftPush("list", "a");
        listOps.leftPushAll("list", "b", "c", "d");
        //取值
        List<String> list = listOps.range("list",0, -1);
        System.out.println(JSON.toJSON(list));
        //获取list size
        Long size = listOps.size("list");
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            //出队列
            String element = (String) listOps.rightPop("list");
            System.out.println(element);
        }
    }

    /**
     * 操作Set类型的数据
     */
    @Test
    public void testSet(){
        SetOperations setOperations = redisTemplate.opsForSet();

        //存值
        setOperations.add("myset","a","b","c","a");

        //取值
        Set<String> myset = setOperations.members("myset");
        for (String o : myset) {
            System.out.println(o);
        }

        //删除成员
        setOperations.remove("myset","a","b");

        //取值
        myset = setOperations.members("myset");
        for (String o : myset) {
            System.out.println(o);
        }
    }

    /**
     * 操作ZSet类型的数据
     */
    @Test
    public void testZset(){
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        //存值
        zSetOperations.add("myZset","a",10.0);
        zSetOperations.add("myZset","b",11.0);
        zSetOperations.add("myZset","c",12.0);
        zSetOperations.add("myZset","a",13.0);

        //取值
        Set<String> myZset = zSetOperations.range("myZset", 0, -1);
        for (String s : myZset) {
            System.out.println(s);
        }

        //修改分数
        zSetOperations.incrementScore("myZset","b",20.0);

        //取值
        myZset = zSetOperations.range("myZset", 0, -1);
        for (String s : myZset) {
            System.out.println(s);
        }

        //删除成员
        zSetOperations.remove("myZset","a","b");

        //取值
        myZset = zSetOperations.range("myZset", 0, -1);
        for (String s : myZset) {
            System.out.println(s);
        }
    }

    /**
     * 通用操作，针对不同的数据类型都可以操作
     */
    @Test
    public void testCommon(){
        //获取Redis中所有的key
        Set<String> keys = redisTemplate.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }

        //判断某个key是否存在
        Boolean itcast = redisTemplate.hasKey("ericsson");
        System.out.println(itcast);

        //删除指定key
        redisTemplate.delete("myZset");

        //获取指定key对应的value的数据类型
        DataType dataType = redisTemplate.type("myset");
        System.out.println(dataType.name());

    }

}
