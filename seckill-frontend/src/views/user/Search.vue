<!-- 搜索页 -->
<script setup>
import { getGoodsListService } from '@/api/goods';
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const router = useRouter()
const route = useRoute()

const row = ref(5)
const length = ref(0)

//搜索商品
const goodsList = ref([])
const query = ref({
    key: '',
    currentPage: 1,
    pageSize: 20,
})
const searchByKey = async () => {
    const key = route.query.key
    query.value.key = key
    const res = await getGoodsListService(query.value)
    goodsList.value = res.data.data
    length.value = goodsList.value.items.length
}
searchByKey()

//分页
const handlePageChange = (currentPage) => {
    query.value.currentPage = currentPage
    searchByKey()
}

//点击跳转
const toDetail=(goodsId)=>{
    router.push({
        path:`/detail/${goodsId}`,
        state:{
            isSecKill:false,
        }
    })
}
</script>

<template>
    <div class="main">
        <div class="goodsList">
            <el-empty v-if="length===0" description="暂无商品，换个关键词再搜素吧~" />
            <el-backtop :right="100" :bottom="100" />
            <el-card v-for="goods in goodsList.items" :key="goods.goodsId" shadow="hover"
                @click="toDetail(goods.goodsId)">
                <el-image :src="goods.picture"></el-image>
                <div><el-text>{{ goods.description }}</el-text></div>
                <div><el-text class="price">￥{{ goods.price }}/{{ goods.unit }}</el-text></div>
            </el-card>

            <el-card class="empty" v-for="item in (row - length % row)" v-if="length % row > 0"></el-card>
        </div>
        <el-pagination layout="total,prev, pager, next" 
            :total="goodsList.total" :pageSize="query.pageSize" 
            :currentPage="query.currentPage" @current-change="handlePageChange" 
            background hide-on-single-page
         />
        <div class="tip" v-if="(query.currentPage-1)*query.pageSize+length===goodsList.total&&length!=0">
            <div class="dashed"></div>
            <el-text>没有更多了~</el-text>
            <div class="dashed"></div>
        </div>
    </div>
</template>

<style scoped>
.main {
    padding: 0 20px;
    margin-bottom: 20px;
}
.el-empty {
    margin: 0 auto;
}
.goodsList {
    display: flex;
    flex-wrap: wrap;
    /* justify-content: space-around; */
}

.el-card {
    width: 18%;
    height: 250px;
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

.el-image {
    width: 80%;
    /* height: 180px; */
}

.el-pagination {
    display: flex;
    justify-self: center;
    margin-top: 20px;
}

.tip {
    display: flex;
    justify-self: center;
    align-items: center;
    margin-top: 30px;
}
.dashed {
    width: 350px;
    height: 1px;
    margin: 0px 20px;
    border-top: 2px dashed var(--el-border-color);
}
</style>