package com.example.utils;

import java.util.Map;

/**
 * 维护当前线程中变量的副本
 * (主要用于存储当前发送请求的用户id)
 */
@SuppressWarnings("all")
public class ThreadLocalUtil {
    //提供ThreadLocal对象
    public static final ThreadLocal THREAD_LOCAL=new ThreadLocal();

    public static <T> T get(){
        return (T)THREAD_LOCAL.get();
    }

    public static void set(Object value){
        THREAD_LOCAL.set(value);
    }

    /**
     * 从ThreadLocal中取出用户id
     * @return 当前登录用户id
     */
    public static Integer getUserId(){
        Map<String,Object> map= get();
        Integer userId= (Integer) map.get("userId");
        System.out.println("userId="+userId);
        return userId;
    }

    /**
     * 清除ThreadLocal 防止内存泄漏
     */
    public static void remove(){
        THREAD_LOCAL.remove();
    }

}
