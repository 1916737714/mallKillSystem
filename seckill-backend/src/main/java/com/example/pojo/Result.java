package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 统一响应体
 */
@Data
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T>Result<T> success(String msg,T data){
        return new Result<>(0, msg, data);
    }

    public static Result success(String msg){
        return new Result(0, msg, null);
    }

    public static Result error(String msg){
        return new Result(1,msg,null);
    }
}
