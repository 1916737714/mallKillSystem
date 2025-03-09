package com.example.service;

import com.example.pojo.User;

public interface UserService {
    User findUserByTel(String tel);

    void addUser(User user);

    User findUserById();
}
