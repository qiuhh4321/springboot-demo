package com.example.demo.service;

import com.example.demo.entity.NarMenu;
import com.example.demo.entity.User;
import com.example.demo.utils.PageResult;

import java.util.List;

public interface UserService {

    public List<NarMenu> getMenu();

    public List<User> getUser();

    public User login(String username, String password);

    public PageResult queryUser(Integer page);

//    public String getString(String key);

    public User Login(String username);
//
//    /*
//    登录限制检查
//     */
//    public String loginValdate(String username);
//
//    /*
//    是否被锁定
//     */
//    public Map<String,Object> loginUserLock(String username);

    List<User> findAllPage(int before,int after);

    int count();

    int insertOne(User user);

}
