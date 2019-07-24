package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class TestController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping()
    public void usernameAndPassword(String username,String password){

    }

    @PostMapping("/test")
    public void getarray(String str)throws Exception{
        String[] strings=str.split(",",-1);
        for(String string :strings){
            if("".equals(string))
                System.out.println("*******");
            else
                System.out.println(string);
        }
        System.out.println(strings.length);
    }


//    @GetMapping("/getuser")
//    public JSONResult test() throws Exception{
//        System.out.println("******");
//        List<User> users=userService.getUser();
//        System.out.println(users.toString());
//        return JSONResult.ok(users);
//    }

    @RequestMapping("/getuser2")
    public JSONResult test2() throws Exception{
//        List<User> users=userService.getUser();
//        System.out.println(users.toString());
//        return JSONResult.ok(users);
        redisTemplate.opsForValue().set("test","sadasdsa",10, TimeUnit.SECONDS);
        return JSONResult.errorMsg("123");
    }
    @RequestMapping("/thread")
    public void test3(){
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
    @GetMapping("/login")
    public User findUser(int id) {
        User user = userService.login(id);
        System.out.println("************");
        return user;
    }

    @GetMapping("/delRedis")
    @CacheEvict(value="thisredis", key="'users_'+#id")
    public void delUser(Integer id) {
        // 删除user
        System.out.println(id);
        System.out.println("*************");
    }


    @RequestMapping("/page")
    public JSONResult test4(Integer page){
        if(page==null)
            page=1;
        return JSONResult.ok(userService.queryUser(page));
    }


}
