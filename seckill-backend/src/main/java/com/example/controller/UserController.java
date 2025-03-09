package com.example.controller;

import com.example.pojo.Result;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.utils.JwtUtil;
import com.example.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Result register( User user){

       User u= userService.findUserByTel(user.getTel());
       System.out.println("user="+u);
       if(u!=null){
           return Result.error("该账号已注册");
       }
       userService.addUser(user);
       return Result.success("注册成功");
    }


    @PostMapping("/login")
    public Result login(User user){

        System.out.println("password="+user.getPassword());
        User u=userService.findUserByTel(user.getTel());

        System.out.println("user="+u);
        if(u==null){
            return Result.error("该账号不存在");
        }
        String password= Md5Util.getMD5String(user.getPassword());
        if(password.equals(u.getPassword())){
            Map<String,Object> claim=new HashMap<>();
            claim.put("userId",u.getUserId());
            System.out.println("user="+claim);

            String token= JwtUtil.genToken(claim); //生成token
            return Result.success("登陆成功",token);
        }
        return Result.error("密码错误");
    }

    //获取当前登录用户详细信息
    @GetMapping("/detail")
    public Result<User> detail(){
        System.out.println("user=");
        User user=userService.findUserById();
        System.out.println("user="+user);
        return Result.success("成功获取个人信息",user);
    }


}
