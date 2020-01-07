package com.example.demo.mapper;

import com.example.demo.entity.NarMenu;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
     List<User> getUser();

     User login(@Param("name") String username, @Param("pwd") String password);

     List<User> findAllPage(@Param("before")int before,@Param("after")int after);

     int count();

     int insertOne(User user);

     List<NarMenu> getMenu();

     List<NarMenu> getMenuChildren(Integer id);

}
