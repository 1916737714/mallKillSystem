<!-- 商品详情页 -->
<script setup>
import { useRoute, useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import { Plus, Minus } from '@element-plus/icons-vue'
import { getGoodsDetailService } from '@/api/goods';
import { useCartStore } from '@/stores/cart';
import { addToCartService } from '@/api/cart';
import { useUserStore } from '@/stores/user';
import { ElMessage } from 'element-plus';
import { getSecKillDetailService } from '@/api/seckill';
const cartStore = useCartStore()
const userStore = useUserStore()
const route = useRoute()
const router = useRouter()

const isSecKill = ref(false)
//获取详情页内容
const goods = ref([])
const secKill = ref([])
const getDetail = async () => {
    let res
    isSecKill.value = history.state.isSecKill
    if (isSecKill.value) {
        res = await getSecKillDetailService(route.params.id)
        secKill.value = res.data.data
        let status
        const nowTime = ref(new Date())
        const startTime = ref(new Date(secKill.value.startTime))
        const endTime = ref(new Date(secKill.value.endTime))
        if (startTime.value >= nowTime.value) {
            status = 1 //未开始
        } else if (endTime.value <= nowTime.value) {
            status = 0
        } else if (startTime.value < nowTime.value && endTime.value > nowTime.value) {
            status = 2
        }
        secKill.value = { ...secKill.value, status: status }
    } else {
        res = await getGoodsDetailService(route.params.id)
        goods.value = res.data.data
    }
}
getDetail()

//数量改变
let num = ref(1)
const handleNumChange = (change) => {
    num.value += change
}

//加入购物车
const addToCart = async () => {
    const cart = cartStore.findGoodsInCart(goods.value.goodsId)
    if (cart) {
        await cartStore.updateNum({
            cartId: cart.cartId,
            goodsId: cart.goodsId,
            goodsNum: cart.goodsNum + num.value
        })
        ElMessage.success("添加购物车成功")
    } else {
        const res = await addToCartService({
            userId: userStore.userInfo.userId,
            goodsId: goods.value.goodsId,
            goodsNum: num.value
        })
        ElMessage.success(res.data.msg)
    }
}

//下单
const goToPay = () => {
    const goodsInfo = ref({})
    if (isSecKill.value) {
        goodsInfo.value = { ...secKill.value, goodsNum: num.value, isSecKill: true }
    } else {
        goodsInfo.value = { ...goods.value, goodsNum: num.value, isSecKill: false }
    }
    router.push({
        path: '/pay',
        state: {
            goodsInfo: { ...goodsInfo.value }
        }
    })
}

</script>

<template>
    <div class="main">
        <div class="goodsInfo" v-if="!isSecKill">
            <el-image :src="goods.picture"></el-image>
            <div class="detail">
                <el-text class="desc">{{ goods.description }}</el-text>
                <el-text class="price">￥{{ goods.price }}/{{ goods.unit }}</el-text>
                <el-text class="stock">库存：剩余 {{ goods.stock }} 件</el-text>
                <el-text>保障：退货运费险 7天无理由退货 极速退款</el-text>
                <el-text>快递：免运费 预计明天发货｜承诺48小时内发货，晚发必赔</el-text>
                <el-text>数量：
                    <el-input v-model="num" class="counter">
                        <template #prefix>
                            <el-button @click="handleNumChange(-1)" link :icon="Minus"
                                :disabled="num === 1"></el-button>
                        </template>
                        <template #suffix>
                            <el-button @click="handleNumChange(1)" link :icon="Plus"
                                :disabled="num === goods.stock"></el-button>
                        </template>
                    </el-input>
                </el-text>

                <div class="btnClass">
                    <el-button class="btn" @click="addToCart" color="#ff5000" plain>加入购物车</el-button>
                    <el-button class="btn" @click="goToPay" color="#ff5000">立即购买</el-button>
                </div>
            </div>
        </div>
        <div class="goodsInfo" v-else>
            <el-image :src="secKill.picture"></el-image>
            <div class="detail">
                <el-text class="desc">{{ secKill.description }}</el-text>
                <el-text class="price">秒杀价：￥{{ parseFloat(secKill.secKillPrice).toFixed(2) }}/{{ secKill.unit }}</el-text>
                <el-text tag="del">原价：￥{{ parseFloat(secKill.price).toFixed(2) }}/{{ secKill.unit }}</el-text>
                <el-text>保障：退货运费险 7天无理由退货 极速退款</el-text>
                <el-text>快递：免运费 预计明天发货｜承诺48小时内发货，晚发必赔</el-text>
                <el-text>库存：{{ secKill.secKillStock }} 件</el-text>
                <div class="timer">
                    <el-text v-if="secKill.status === 1">活动将于 {{ secKill.startTime }} 开始</el-text>
                    <el-text v-if="secKill.status === 2">活动进行中，将于 {{ secKill.endTime }} 结束</el-text>
                    <!-- <el-countdown v-if="secKill.status===2" format="HH:mm:ss" :value="Date.now() + 1000 * 6 * 24">
                    <template #prefix>
                        <el-text>活动进行中，距离结束还有{{ secKill.endTime }}</el-text>
                    </template>
                </el-countdown> -->
                </div>
                <el-text>
                    数量：
                    <el-input v-model="num" class="counter">
                        <template #prefix>
                            <el-button @click="handleNumChange(-1)" link :icon="Minus"
                                :disabled="num === 1"></el-button>
                        </template>
                        <template #suffix>
                            <el-button @click="handleNumChange(1)" link :icon="Plus"
                                :disabled="num === secKill.limitNum"></el-button>
                        </template>
                    </el-input>
                    每个账号限购 {{ secKill.limitNum }} 件
                </el-text>
                <el-button class="btn" @click="goToPay" color="#ff5000"
                    :disabled="secKill.status === 1 || secKill.secKillStock === 0">{{ secKill.secKillStock === 0 ? '已售罄'
                    :'立即抢购' }}</el-button>
            </div>
        </div>
    </div>
</template>

<style scoped>
.main {
    display: flex;
    justify-content: center;
    align-items: center;
    height: calc(100vh - 60px - 40px );
    width: 100%;
}

.goodsInfo {
    width: 90%;
    height: 80%;
    display: flex;
    flex-direction: row;
}

.el-image {
    width: 40%;
}

.detail {
    width: 60%;
    margin-left: 5%;
    margin-top: 5%;
    display: flex;
    flex-direction: column;
}

.el-text {
    align-self: flex-start;
    margin-bottom: 15px;
}

.desc {
    font-size: xx-large;
    color: black;
}

.price {
    font-size: larger;
    color: #ff5000;
    font-weight: bold;
}

.counter {
    width: 120px;
    margin: 20px 10px 20px 0px;
}

:deep(.counter .el-input__inner) {
    text-align: center;
}

.btn {
    width: 200px;
    /* background-color: #ff5000; */
    /* color: white; */
    /* margin: 20px 0px; */
}
</style>