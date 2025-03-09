import { ref } from 'vue'
import { defineStore } from 'pinia'
import { getCartListService, updateNumService } from '@/api/cart'
import { useUserStore } from './user'

export const useCartStore = defineStore('cart', () => {
    //cart
    const cartList=ref([])
    const selectedCartList=ref([])
    //获取购物车列表
    const getCartList=async()=>{
        const userStore=useUserStore()
        const res=await getCartListService(userStore.userInfo.userId)
        cartList.value=res.data.data
    }
    //商品是否在购物车中
    const findGoodsInCart=(goodsId)=>{
        const goods=cartList.value.find(cart=>cart.goodsId===goodsId)
        return goods
    }
    //更新购物车数量
    const updateNum=async(cart)=>{
        const userStore=useUserStore()
        const res = await updateNumService({
            cartId: cart.cartId,
            userId: userStore.userInfo.userId,
            goodsId: cart.goodsId,
            goodsNum: cart.goodsNum
        })
    }
    //获取已选择购物车列表
    const setSelectedCart=(carts)=>{
        // console.log("carts",carts)
        selectedCartList.value=carts
        console.log("selectedCartList",selectedCartList.value)
    }
    
    return { 
        cartList,selectedCartList,
        getCartList,findGoodsInCart,updateNum,setSelectedCart
    }
  },
  {
    persist:true //持久化
  }
)
