# mallKillSystem

**这是一个简单的商城秒杀系统，能够应付大作业**

---
# 本项目使用的技术
- 前端使用Vue + element 实现 有客户端和管理员端
- 后端使用SpringBoot + Mybatis + Mysql 实现
- 图片存储 使用阿里云oos对象存储服务
  
  ---

# 项目预览

- seckill-backend 存放后端项目 

- seckill-frontend 存放前端项目

- 资源图片 上传到阿里云oss的资源

- db_seckill.sql 数据库导入文件
---
# 跑通该项目需要的工具
1. VSCODE(推荐)  [vscode下载地址](https://code.visualstudio.com/)
2. Mysql [Mysql安装教程](https://code.visualstudio.com/)
3. idea或者其他可以跑java项目的编辑器
4. 配置maven源(默认配置好了)

---

# 如何跑通该项目

- ***在阿里云开通oos对象存储服务，将资源图片文件夹的图片全部上传到该服务下***
  1. **注册登录阿里云,开通阿里云oos服务[阿里云网址](https://www.aliyun.com/)并获取密钥**
    ![alt text](./截图图片(在README中的图片)/QQ_1741599746192.png)  
    2. **开通服务后创建Bucket**
    ![alt text](./截图图片(在README中的图片)/QQ_1741600353424.png)
   **创建bucket如图中一致，如果不一致，会导致商品链接失效，要重新改db_seckill数据库goods中picture字段的地址**
   ![alt text](./截图图片(在README中的图片)/QQ_1741600487673.png)
   **创建BUCket成功后关闭阻止公共读服务**
   ![alt text](./截图图片(在README中的图片)/QQ_1741600712389.png)
    3. 上传图片  
    ![alt text](./截图图片(在README中的图片)/QQ_1741600920571.png)
    ---
    ![alt text](./截图图片(在README中的图片)/QQ_1741600972313.png)
    
    ![alt text](./截图图片(在README中的图片)/QQ_1741601070663.png)
    ---
    ![alt text](./截图图片(在README中的图片)/QQ_1741601015727.png)
    
    ![alt text](./截图图片(在README中的图片)/QQ_1741601173596.png)
   
    4. 获取参数以及密钥
    ![alt text](./截图图片(在README中的图片)/QQ_1741602013799.png)
    ---
    ![alt text](./截图图片(在README中的图片)/QQ_1741602150376.png)
    
    ![alt text](./截图图片(在README中的图片)/QQ_1741602189294.png)

    ![alt text](./截图图片(在README中的图片)/QQ_1741602266522.png)
    **把AKID跟AKS填写到下面的参数中并保存号，后续会用到**

    ```
    domain="https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/";
    endPoint="https://oss-cn-shenzhen.aliyuncs.com";
    accessKeyId="";
    accessKeySecret="";
    bucketName="umlimagesave";

    ```


- ***在mysql中建立数据库db_seckill,将db_seckill.sql导入新建的数据库中***
    1. 连接本地数据库，打开控制台
    ![alt text](./截图图片(在README中的图片)/QQ_1741602626137.png)
    2. 创建db_seckill数据库成功后，将db_seckill.sql导入
    ![alt text](./截图图片(在README中的图片)/QQ_1741602731640.png)
    **导入成功后就会看到数据**
    ![alt text](./截图图片(在README中的图片)/QQ_1741602793193.png)

- ***打开后端项目更改某些参数***
    1. 填写刚才获取到的密钥
    ![alt text](./截图图片(在README中的图片)/QQ_1741603162750.png)
    2. 这里填写登录本地mysql数据库的账户密码
    ![alt text](./截图图片(在README中的图片)/QQ_1741603225412.png)

- **到这里可以启动后端了**

- **启动前端**
    管理员：账户19116737714 密码：123456
    用户：可以注册一个登录

# 效果预览
  
![alt text](./截图图片(在README中的图片)/QQ_1741603634108.png)
---
- **管理员端**
![alt text](./截图图片(在README中的图片)/QQ_1741603657388.png)
- **用户端**
![alt text](./截图图片(在README中的图片)/QQ_1741603744538.png)
