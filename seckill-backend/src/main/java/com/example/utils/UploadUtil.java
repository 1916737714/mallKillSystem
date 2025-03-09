package com.example.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class UploadUtil {
    private static final String domain="";
    private static final String endPoint="";

    private static final String bucketName="";

    /**
     * 上传图片文件到oss
     * @param file 图片文件
     * @return 图片url
     */
    public static String uploadImage(MultipartFile file) {
        //生成文件名
        String originalFileName=file.getOriginalFilename(); //原文件名
        int index=originalFileName.lastIndexOf("."); //最后一个.的位置
        String ext=originalFileName.substring(index); //获取后缀
        String uuid= UUID.randomUUID().toString();
        String fileName=uuid+ext;
        OSS oss=new OSSClientBuilder().build(endPoint,,);
        try{
            oss.putObject(
                    bucketName,
                    fileName,
                    file.getInputStream()
            );
            oss.shutdown();
            return domain+fileName;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据文件名删除oss上的文件
     * @param url 图片url
     */
    public static void deleteImage(String url){
        // 填写文件完整路径。文件完整路径中不能包含Bucket名称。（这里根据自己的地址改）
        String fileName = url.replace(domain+"/", "");
        System.out.println("拆分后的路径"+fileName);
        OSS ossClient = new OSSClientBuilder().build(endPoint,,);
        ossClient.deleteObject(bucketName, fileName);
        ossClient.shutdown();
    }
}
