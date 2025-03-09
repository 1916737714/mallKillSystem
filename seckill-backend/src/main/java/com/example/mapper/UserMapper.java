package com.example.mapper;

import com.example.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where tel=#{tel}")
    User findUserByTel(String tel);

    @Insert("insert into user(username,tel,password,role) values(#{username},#{tel},#{password},#{role})")
    void addUser(User user);

    @Select("select * from user where user_id=#{id}")
    User findUserById(Integer id);
}
