<!-- 搜索页 -->
<script setup>
import { getSeKillListService } from '@/api/seckill';
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const router = useRouter()
const route = useRoute()

const row=ref(5)
const length = ref(0)
//获取列表
const query = ref({
    currentPage: 1,
    pageSize: 20,
})
const secKillList=ref([])
const getSecKillList=async()=>{
    const res=await getSeKillListService(query.value)
    secKillList.value=res.data.data
    length.value = secKillList.value.items.length
}
getSecKillList()

//分页
const handlePageChange = (currentPage) => {
    query.value.currentPage = currentPage
}

const toDetail=(secKillId)=>{
    router.push({
        path:`/detail/${secKillId}`,
        state:{
            isSecKill:true,
        }
    })
}

//tab切换
const selectChange=(index)=>{
    console.log(index)
}
</script>

<template>
    <!-- <el-menu mode="horizontal" @select="selectChange">
        <div class="menuBox">
            <el-menu-item index="1">全部秒杀</el-menu-item>
            <el-menu-item index="2">即将结束</el-menu-item>
            <el-menu-item index="3">正在进行</el-menu-item>
            <el-menu-item index="4">即将开始</el-menu-item>
        </div>
    </el-menu> -->
    <div class="main">
        <div class="goodsList">
            <el-backtop :right="100" :bottom="100" />
            <el-card v-for="secKill in secKillList.items" :key="secKill.secKillId" shadow="hover" @click="toDetail(secKill.secKillId)">
                <el-image :src="secKill.picture" class="pic"></el-image>
                <div><el-text>{{ secKill.description }}</el-text></div>
                <div><el-text class="price">￥{{ secKill.secKillPrice }}/{{ secKill.unit }}</el-text></div>
                <div><el-text tag="del">￥{{ secKill.price }}/{{ secKill.unit }}</el-text></div>
            </el-card>
            <el-card class="empty" v-for="item in (row - length % row)" v-if="length % row > 0"></el-card>
        </div>
        <el-pagination layout="total,prev, pager, next" :total="secKillList.total" :pageSize="query.pageSize" :currentPage="query.currentPage"
            :background="true" @current-change="handlePageChange" />
    </div>
</template>

<style scoped>

.el-menu {
    width: 100%;
    display: flex;
    justify-content: center;
    position: fixed;
    top: 50px;
    z-index: 10000;
}
.menuBox {
    width: 50%;
    display: flex;
    justify-content: space-around;
}
.el-menu-item:hover{
    background-color: white;
}
.el-menu-item .is-active {
    /* width: 0; */
    /* height: auto; */
    /* border-radius: 50px; */
    /* background-color: rgb(72, 26, 26); */
    background-color: white;
}
.main {
    padding: 0 20px;
    margin-bottom: 20px;
    margin-top: 40px;
}
.goodsList {
    display: flex;
    flex-wrap: wrap;
    /* justify-content: space-around; */
}

.el-card {
    width: 18%;
    /* height: 250px; */
    margin: 10px auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
}
.price {
    font-size: medium;
    color: #ff5000;
}
.empty {
    width: 18%;
    visibility: hidden
}

.pic {
    width: 80%;
    /* height: 180px; */
}

.el-pagination {
    display: flex;
    justify-self: center;
    margin-top: 20px;
}

</style>