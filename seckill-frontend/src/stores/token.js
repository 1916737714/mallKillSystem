import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useTokenStore = defineStore('token', () => {
    //token
    const token=ref('')
    const setToken=(newToken)=>{
      token.value=newToken
    }
    const removeToken=()=>{
      token.value=''
      localStorage.removeItem('token')
    }

    return { 
      token,setToken,removeToken
    }
  },
  {
    persist:true //持久化
  }
)
