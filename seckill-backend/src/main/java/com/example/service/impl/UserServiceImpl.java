package com.example.service.impl;

import com.example.mapper.UserMapper;
import com.example.pojo.User;
import com.example.service.UserService;
import com.example.utils.Md5Util;
import com.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public User findUserByTel(String tel) {
        return userMapper.findUserByTel(tel);
    }

    @Override
    public void addUser(User user) {
        user.setPassword(Md5Util.getMD5String(user.getPassword()));
        user.setRole(2);
        userMapper.addUser(user);
    }

    @Override
    public User findUserById() {
        Integer id= ThreadLocalUtil.getUserId();

        System.out.println("id="+id);
//        Integer id= 1;
        User user=userMapper.findUserById(id);
        return user;
    }


}
