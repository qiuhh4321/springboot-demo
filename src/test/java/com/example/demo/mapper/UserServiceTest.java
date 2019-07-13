package com.example.demo.mapper;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Test
    public void queryUser(){
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for(int i=0;i<100;i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    userService.getUser();
                }
            });
        }
    }

    @Test
    public void t1(){
        System.out.println(userMapper.login(1).toString());
    }

    @Test
    public void redis(){
       redisTemplate.opsForValue().set("java1803","测试数据",2, TimeUnit.HOURS);
    }

    @Test
    public void limitLogin(){

        String username="lijun";
        Map<String,Object> map=userService.loginUserLock(username);
        if((boolean)map.get("flag")){
            System.out.println("失败"+username+"超过次数禁止登录"+map.get("lockTime")+"分钟");
        }else{
            User user=userService.Login(username);
            if(null!=user){

            }else {//登录不成功
              String result= userService.loginValdate(username);
                System.out.println(result);
            }
        }
    }
}
