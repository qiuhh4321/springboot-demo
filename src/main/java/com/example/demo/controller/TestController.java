package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.JSONResult;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
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


//    @GetMapping()
//    public void usernameAndPassword(String username,String password){
//
//    }

    @PostMapping("/test")
    public void getarray(String str) throws Exception {
        String[] strings = str.split(",", -1);
        for (String string : strings) {
            if ("".equals(string))
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
    @CrossOrigin
    @RequestMapping("/getuser2")
    public JSONResult test2() throws Exception {
//        List<User> users=userService.getUser();
//        System.out.println(users.toString());
//        return JSONResult.ok(users);
        redisTemplate.opsForValue().set("test", "sadasdsa", 10, TimeUnit.SECONDS);
        return JSONResult.errorMsg("123");
    }

    @RequestMapping("/thread")
    public void test3() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    userService.getUser();
                }
            });
        }
    }

    @PostMapping("/login")
    public JSONResult findUser(String username, String password) {
        User user = userService.login(username, password);
        if (null == user) {
            return JSONResult.errorMsg("账号或密码错误");
        }
        return JSONResult.ok(user);
    }

    @GetMapping("/delRedis")
    @CacheEvict(value = "thisredis", key = "'users_'+#id")
    public void delUser(Integer id) {
        // 删除user
        System.out.println(id);
        System.out.println("*************");
    }


    @RequestMapping("/page")
    public JSONResult test4(Integer page) {
        if (page == null)
            page = 1;
        return JSONResult.ok(userService.queryUser(page));
    }

    @CrossOrigin
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public JSONResult upload(String userId, String desc, MultipartFile file) throws Exception {

        System.out.println(userId + "********" + desc);
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空...");
        }

        String LNUIX_FILE_SPACE = "/usr/local/include";
        String FILE_SPACE = "D:/test_video";
        String uploadPathDB = "/" + userId + "/video";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        String finalVideoPath = "";
        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    finalVideoPath = FILE_SPACE + uploadPathDB + "/" + fileName;
                    // 设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);
                    System.out.println(uploadPathDB);
                    File outFile = new File(finalVideoPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } else {
                return JSONResult.errorMsg("上传出错...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.errorMsg("上传出错...");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        return JSONResult.ok();
    }

    @PostMapping(value = "/uploadLinux", headers = "content-type=multipart/form-data")
    public JSONResult uploadLinux(String userId, String desc, MultipartFile file) throws Exception {

        System.out.println(userId + "********" + desc);
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空...");
        }

        String FILE_SPACE = "/usr/local/include";
        String uploadPathDB = "/" + userId ;
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        String finalVideoPath = "";
        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    finalVideoPath = FILE_SPACE + uploadPathDB + "/" + fileName;
                    // 设置数据库保存的路径
                    uploadPathDB ="/pic"+uploadPathDB+"/"+fileName;
                    System.out.println(uploadPathDB);
                    File outFile = new File(finalVideoPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } else {
                return JSONResult.errorMsg("上传出错...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.errorMsg("上传出错...");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        return JSONResult.ok(uploadPathDB);
    }
    @CrossOrigin
    @RequestMapping("/findallEmp")
    public  String findallEmp(Integer page, Integer limit,String username){
        System.out.println(page+"**"+limit+"**"+username);
        if(null==page)
            page=1;
        if(null==limit)
            limit=10;
        int before = limit * (page - 1);
        int after =  limit;
        //带参数从数据库里查询出来放到eilist的集合里
        List<User> users = userService.findAllPage(before, after);
        int count = userService.count();
        //用json来传值
        JSONArray json = JSONArray.fromObject(users);
        String js = json.toString();
        //*****转为layui需要的json格式，必须要这一步
        String jso = "{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+js+"}";
        return jso;
    }


}
