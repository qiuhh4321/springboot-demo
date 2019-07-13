package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.utils.PageResult;

import java.util.List;
import java.util.Map;

public interface UserService {
    public List<User> getUser();

    public User login(Integer id);

    public PageResult queryUser(Integer page);

    public String getString(String key);

    public User Login(String username);

    /*
    登录限制检查
     */
    public String loginValdate(String username);

    /*
    是否被锁定
     */
    public Map<String,Object> loginUserLock(String username);

}
