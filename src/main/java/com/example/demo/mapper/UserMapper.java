package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    public List<User> getUser();

    public User login(@Param("id") Integer id);


}
