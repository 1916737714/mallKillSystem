package com.example.interceptor;

import com.example.utils.JwtUtil;
import com.example.utils.ThreadLocalUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token=request.getHeader("Authorization"); //从请求头中获取token
        try{
            // TODO 可完成基本接口后再打开
            Map<String,Object> claims= JwtUtil.parseToken(token); //解析token
            ThreadLocalUtil.set(claims); //将数据（一般为用户id）放入ThreadLocal
            return true; //放行
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        ThreadLocalUtil.remove(); //清除ThreadLocal
    }
}
