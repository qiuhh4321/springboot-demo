package com.example.demo.entity;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 3628093215942351608L;
    private Integer id;
    private String name;
    private String age;

    public static String getKetName(){
        return "user:";
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public static String getLoginTimeLockKey(String username){
        return "user:loginTime:lock:"+username;
    }
    public static  String getLoginCountFailKey(String username){
        return "user:LoginCount:fail"+username;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
