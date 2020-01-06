package com.example.demo.controller;

import com.example.demo.entity.NarMenu;
import com.example.demo.service.UserService;
import com.example.demo.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        return "login";
    }

    @RequestMapping("/toLogin")
    public String toLogin(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("root", request.getContextPath());
        return "login";
    }

    @GetMapping("/test")
    @CrossOrigin
    @ResponseBody
        public JSONResult paramTest(String id){
        List<NarMenu> narMenus=userService.getMenu();
     return JSONResult.ok(narMenus);
    }

    @RequestMapping("/weapp/login")
    public void login(){
        System.out.println("*******");
    }


}
