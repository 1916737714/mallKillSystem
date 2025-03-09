import axios from 'axios'
import router from '@/router';
import { useTokenStore } from '@/stores/token';

// 基础配置
const instance=axios.create({
    // baseURL:'http://localhost:8084',
    baseURL:'/api',
    timeout:10000
})

// 请求拦截器
instance.interceptors.request.use(
    (config) =>{
        const tokenStore=useTokenStore()
        if(tokenStore.token){
            config.headers.Authorization=tokenStore.token //携带token
        }
        return config;
    }, 
    (error) =>{
        return Promise.reject(error);
    }
);

// 响应拦截器
instance.interceptors.response.use(
    (response)=> {
        console.log("response "+response.data.msg,response)
        if(response.data.code===0){
            return response;
        }
        ElMessage.error(response.data.msg||'服务异常')
        return Promise.reject(response.data);
    }, 
    (error) =>{
        if(error.response?.status===401){
            router.push('/') //未携带token,跳转登录页
        }
        console.log("error"+error)
        ElMessage.error(response.data.msg||'服务异常')
        return Promise.reject(error);
    }
);

export default instance
