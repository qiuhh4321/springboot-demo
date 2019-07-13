package com.example.demo.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPoolUtil {
    private static JedisPool jedisPool;
    static {
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxTotal(5);
        poolConfig.setMaxIdle(1);
        jedisPool=new JedisPool(poolConfig,"10.28.37.100",6379);
    }

    public static Jedis getJedis(){
        return (Jedis)jedisPool.getResource();
    }

    public static void close(Jedis jedis){
        jedis.close();
    }
}
