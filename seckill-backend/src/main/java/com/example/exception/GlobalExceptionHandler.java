package com.example.exception;

import com.example.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    //系统所有异常
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        e.printStackTrace();
        return Result.error("服务器错误");
    }

    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e){
        e.printStackTrace();
        return Result.error(e.getMsg());
    }
}
