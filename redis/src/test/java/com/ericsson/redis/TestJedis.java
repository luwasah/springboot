package com.ericsson.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Java 操作redis之Jedis示例全集: 类似Java操作mysql之jdbc
 */
public class TestJedis {

    @Test
    public void test(){
        //建立连接
        Jedis jedis = new Jedis("192.168.100.100", 6379);
        //连接redis密码
        jedis.auth("123456");
        //操作redis
        jedis.set("name", "tom");
        String name = jedis.get("name");
        System.out.println("--------name: " + name);
        //关闭连接
        jedis.close();


    }

}
