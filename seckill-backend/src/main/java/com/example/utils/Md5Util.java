package com.example.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 密码加密
 * (主要关注getMD5String方法，用于注册时的密码加密及登录时的密码校验)
 */
public class Md5Util {
    /**
     * 默认的密码字符串组合，用来将字节转换成16进制表示的字符
     */
    protected static char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
    protected static MessageDigest messageDigest=null;

    static {
        try{
            messageDigest=MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsae) {
            System.err.println(Arrays.toString(Md5Util.class.getClasses()) +"初始化失败,MessageDigest不支持MD5Util");
            nsae.printStackTrace();
        }
    }

    /**
     * 判断字符串的md5校验码是否与一个已知的md5码相匹配
     * @param password  要校验的字符串
     * @param md5PwdStr 已知的md5校验码
     */
    public static boolean checkPassword(String password,String md5PwdStr){
        String s=getMD5String(password);
        return s.equals(md5PwdStr);
    }

    /**
     * 将明文转换为密文(对密码加密)
     * @param password 未加密密码
     * @return 加密后的密码
     */
    public static String getMD5String(String password){
        byte[] bytes=password.getBytes();
        messageDigest.update(bytes);
        return bufferToHex(messageDigest.digest());
    }

    /**
     * 以下为用于进行类型转换的代码，可以不用关注
     */

    private static String bufferToHex(byte[] bytes){
        return bufferToHex(bytes,0,bytes.length);
    }

    private static String bufferToHex(byte[] bytes,int m,int n){
        StringBuffer stringBuffer=new StringBuffer(2*n);
        int k=m+n;
        for(int i=m;i<k;i++){
            appendHexPair(bytes[i],stringBuffer);
        }
        return stringBuffer.toString();
    }

    private static void appendHexPair(byte bt,StringBuffer stringBuffer){
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>>
        // 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
        stringBuffer.append(c0);
        stringBuffer.append(c1);
    }
}

