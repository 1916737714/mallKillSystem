import { useTokenStore } from '@/stores/token'
import { useUserStore } from '@/stores/user'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', component: () => import('@/views/UserLogin.vue') },
    { 
      path: '/home', 
      component: () => import('@/views/user/Layout.vue'),
      // redirect:'/home',
      meta:{ requiresAuth:true,role:2 },
      children:[
        { path: '/home', component: () => import('@/views/user/Home.vue'),name:'Home' },
        { path: '/list', component: () => import('@/views/user/Search.vue') }, 
        { path: '/search', component: () => import('@/views/user/Search.vue'),name:'Search' }, 
        { path: '/seckill', component: () => import('@/views/user/SecKill.vue') },
        { path: '/detail/:id', component: () => import('@/views/user/GoodsDetail.vue'),name:'Detail' },
        { path: '/cart', component: () => import('@/views/user/Cart.vue') },
        { path: '/pay', component: () => import('@/views/user/Pay.vue'),name:'Pay' },
        { path: '/order', component: () => import('@/views/user/Orders.vue') },
      ]
    },

    { 
      path: '/admin', 
      component: () => import('@/views/admin/AdminLayout.vue'),
      redirect:'/admin/seckill',
      meta:{ requiresAuth:true,role:1 },
      children:[
        { path: '/admin/seckill', component: () => import('@/views/admin/SecKillManage.vue') },
        { path: '/admin/goods', component: () => import('@/views/admin/GoodsManage.vue') },
      ]
    },
    { path:'/:catchAll(.*)',component:()=>import('@/views/NotFound.vue'),}
  ],
})


router.beforeEach((to,from,next)=>{
  const tokenStore=useTokenStore()
  const userStore=useUserStore()
  console.log(to,from)
  if(to.meta.requiresAuth&&!tokenStore.token){
    next({path:'/'}) //需要登录但没登陆,跳转到登录
  }else if(to.meta.role&&to.meta.role!=userStore.userInfo.role){
    next({path:'/404'}) //需要权限但权限不符
  }else{
    next()
  }
})

export default router
