package com.example.redis;

import com.example.demo.utils.RedisPoolUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

public class RedisDemo {
    private static String host = "39.107.248.218";
    private static int port = 6379;

    public static void main(String[] args) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(5);

        JedisPool pool = new JedisPool(poolConfig, "39.107.248.218", 6379, 10000, "123456");

        Jedis jedis = pool.getResource();
        System.out.println(jedis.ping());

    }

    @Test
    public void t1() {
        Jedis jedis = new Jedis(host, port);
        jedis.set("strName", "名称");
        System.out.println("redis中数据" + jedis.get("strName"));
        jedis.del("strName");
        System.out.println(jedis.get("strName"));
        jedis.close();
    }


    @Test
    public void t2() {
        Jedis jedis = RedisPoolUtil.getJedis();
        String key = "applicationName";
        if (jedis.exists(key)) {
            System.out.println("redis中数据:" + jedis.get(key));
        } else {
            String result = "存入redis";
            jedis.set(key, result);
        }
        RedisPoolUtil.close(jedis);
    }


    @Test
    public void t3() {
        Jedis jedis = RedisPoolUtil.getJedis();
        String key = "users";
        if (jedis.exists(key)) {
            Map<String, String> map = jedis.hgetAll(key);
            System.out.println("redis:" + map.get("id") + " " + map.get("name"));
        } else {
            jedis.hset(key, "id", "1");
            jedis.hset(key, "name", "李俊");
            jedis.hset(key, "age", "1");
            jedis.hset(key, "remark", "素质很低");
            System.out.println("数据库");
        }
    }


}
