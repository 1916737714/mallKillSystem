<!-- 首页 -->
<script setup>
import { Wallet, ShoppingCart, SwitchButton, Search,ChatLineSquare,House } from '@element-plus/icons-vue'
import { nextTick, ref,watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { useTokenStore } from '@/stores/token';
const userStore = useUserStore()
const tokenStore = useTokenStore()
const route=useRoute()
const router=useRouter()

//搜素
const key = ref('')
const toSearch=()=>{
    if(key.value!==''){
        router.push({
            path:'/search',
            query:{
                key:key.value
            }
        })
    }
}
const isHome=ref(true)
const toHome=()=>{
    isHome.value=true
    router.push('/home')
}
watch(
    () => route.name,
    (newName, oldName) => {
        nextTick(()=>{
            if(newName!='Search'){
            console.log("change",newName,oldName)
            key.value=''
            }
            if(newName!='Home'){
                isHome.value=false
            }
        })
    },
    { deep: true, immediate: true }
)

//退出登录
const logout = () => {
    userStore.clearUserInfo()
    tokenStore.removeToken()
    router.push('/')
}

</script>

<template>
    <el-container>
        <el-header>
            <div>
                <el-input v-model="key" placeholder="输入关键词" class="searchBar">
                    <template #suffix>
                        <el-button :icon="Search" link @click="toSearch()"></el-button>
                    </template>
                </el-input>
            </div>
            <div class="userInfo">
                <el-avatar :size="30" :src="userStore.userInfo.avatar" v-if="userStore.userInfo.avatar!=null"></el-avatar>
                <el-avatar :size="30" v-else>{{ userStore.userInfo.username.substring(0,1)}}</el-avatar>
                <el-text class="username" >{{ userStore.userInfo.username }}</el-text>
            </div>
            <el-button @click="router.push('/cart')" link :icon="ShoppingCart">购物车</el-button>
            <el-button @click="router.push('/order')" link :icon="Wallet">我的订单</el-button>
            <el-button link :icon="ChatLineSquare">关于我们</el-button>
            <el-button @click="toHome" link :icon="House" v-if="!isHome">回到首页</el-button>
            <el-button @click="logout" link :icon="SwitchButton">退出登录</el-button>
        </el-header>
        <el-main>
            <router-view :key="$route.fullPath"></router-view>
        </el-main>
    </el-container>
</template>

<style scoped>
.el-header {
    width: 100%;
    position: fixed;
    z-index: 1000;
    display: flex;
    justify-content: right;
    align-items: center;
    box-shadow: 0 0 2px #0000004b;
    background-color: #fff;
}

.el-input {
    width: 300px;
    margin-right: 14px;
}

:deep(.searchBar .el-input__wrapper) {
    border-radius: 100px;
}
.userInfo {
    display: flex; 
    align-items: center;
}
.username {
    margin: 0 12px 0 4px;
}

.el-main {
    margin-top: 60px;
}
</style>