<script setup>
import { useRouter } from 'vue-router';
import notFound from '@/assets/404.png'
const router=useRouter()
const back=()=>{
    router.back()
}
</script>
<template>
    <div class="notFound">
        <div><el-image :src='notFound'></el-image></div>
        <!-- <div><h3>出错了，您访问的页面不存在......</h3></div> -->
        <el-button @click="back" link >返回上一页</el-button>
    </div>
</template>

<style scoped>
.notFound {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background-color: #ffffff;
}
.el-image {
    width: 500px;
    height: 500px;
}
</style>