import request from '@/utils/request'

export const userRegisterService=(regForm)=>{
    // console.log(regForm)
    const params=new URLSearchParams()
    for(let key in regForm){
        params.append(key,regForm[key])
    }
    return request.post('/user/register',params)
}

export const userLoginService=(loginForm)=>{
    // console.log(loginForm)
    const params=new URLSearchParams()
    for(let key in loginForm){
        params.append(key,loginForm[key])
    }
    return request.post('/user/login',params)
}

export const getUserInfoService=()=>{
    return request.get('user/detail')
}
