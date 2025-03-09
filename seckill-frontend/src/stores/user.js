import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', () => {
    //用户信息
    const userInfo=ref({})
    const setUserInfo=(newUserInfo)=>{
      userInfo.value=newUserInfo
    }
    const clearUserInfo=()=>{
      userInfo.value={}
      localStorage.removeItem('user')
    }
    
    return { 
      userInfo,setUserInfo,clearUserInfo,
    }
  },
  {
    persist:true //持久化
  }
)
