package com.example.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

/**
 * 生成/解析token
 */
public class JwtUtil {
    private static final String KEY="llikdnoces";

    /**
     * 接收用户id(或其它业务数据)
     * @param claims 将用户id(或其它业务数据)以map类型存储
     * @return token
     */
    public static String genToken(Map<String,Object> claims){
        return JWT.create()
                .withClaim("claims",claims)
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*6))
                .sign(Algorithm.HMAC256(KEY));
    }

    /**
     * 解析token，并返回存储的数据
     * @param token
     * @return 业务数据(以map类型返回)
     */
    public static Map<String,Object> parseToken(String token){
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }
}
