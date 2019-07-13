package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<Object,Object> objectRedisTemplate;
    @Autowired
    private RedisTemplate<String,String> stringRedisTemplate;
    @Override
    public List<User> getUser() {
        RedisSerializer redisSerializer=new StringRedisSerializer();
        objectRedisTemplate.setKeySerializer(redisSerializer);
        List<User> users= ( List<User>)objectRedisTemplate.opsForValue().get("allUser");

//        双重检测索
        if(null==users) {
            synchronized (this) {
                users = (List<User>) objectRedisTemplate.opsForValue().get("allUser");
                if (null == users) {
                    users = userMapper.getUser();
                    objectRedisTemplate.opsForValue().set("allUser", users);
                    System.out.println("走数据库");
                }else {
                    System.out.println("走redis");
                }
            }
        }else {
            System.out.println("走redis");
        }
        return users;
    }

    @Override
    public User login(Integer id) {
        return userMapper.login(id);
    }

    @Override
    public PageResult queryUser(Integer page) {
        PageHelper.startPage(page,5);
        List<User> list=userMapper.getUser();
        PageInfo<User> pageList = new PageInfo<>(list);
        PageResult pageResult=new PageResult();
        pageResult.setPage(page);
        pageResult.setTotal(pageList.getPages());
        pageResult.setRows(list);
        pageResult.setRecords(pageList.getTotal());
        return pageResult;
    }

    @Override
    public String getString(String key) {
        ValueOperations string=stringRedisTemplate.opsForValue();
        if(stringRedisTemplate.hasKey(key)){
            System.out.println("在redis中取出");
            return (String)string.get(key);
        }else {
            String result="stringRedisTemplate";
            string.set(key,result);
            System.out.println("数据库存入redis");
            return result;
        }
    }

    @Override
    public User Login(String username) {
        return null;
    }

    @Override
    public String loginValdate(String username) {
        int num=5;
        String key=User.getLoginCountFailKey(username);
        if(!stringRedisTemplate.hasKey(key)){

            //分开设置
            stringRedisTemplate.opsForValue().set(key,"1");
            stringRedisTemplate.expire(key,2,TimeUnit.MINUTES);
            return "登录失败,还允许"+(num-1)+"次";
        }else{
            System.out.println("***************");
           Long loginFailCount= Long.parseLong(stringRedisTemplate.opsForValue().get(key));
           if(loginFailCount<num-1){
               stringRedisTemplate.opsForValue().increment(key,1);
              Long time= stringRedisTemplate.getExpire(key,TimeUnit.SECONDS);
               return "登录失败,在"+time+"秒内还允许"+(num-loginFailCount-1)+"次";
           }else {//超过次数
               stringRedisTemplate.opsForValue().set(User.getLoginTimeLockKey(username),"1");
               stringRedisTemplate.expire(User.getLoginCountFailKey(username),1,TimeUnit.HOURS);
               return "超过此数"+num+"次,失效一小时";
           }
        }
    }

    @Override
    public Map<String,Object> loginUserLock(String username) {
        Map<String,Object> map=new HashMap<String, Object>();
        String key=User.getLoginTimeLockKey(username);
        if(stringRedisTemplate.hasKey(key)){
           Long lockTime=stringRedisTemplate.getExpire(key, TimeUnit.MINUTES);
           map.put("flag",true);
           map.put("lockTime",lockTime);
        }else{
            map.put("flag",false);
        }
        return map;
    }



}
