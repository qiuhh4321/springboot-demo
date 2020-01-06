package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private ValueOperations<String, Object> valueOperations;

    @Autowired
    private HashOperations<String, String, Object> hashOperations;

    @Autowired
    private ListOperations<String, Object> listOperations;

    @Autowired
    private SetOperations<String, Object> setOperations;

    @Autowired
    private ZSetOperations<String, Object> zSetOperations;

    @Test
    public void demo1() {
        redisTemplate.opsForValue().set("liming", "sadsad", 20, TimeUnit.MINUTES);
    }


}
