<!-- 订单页 -->
<script setup>
import { cancelOrderService, getOrdersService } from '@/api/order';
import { useUserStore } from '@/stores/user';
import { ElMessage } from 'element-plus';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
const userStore = useUserStore()
const router = useRouter()
//获取订单列表
const orders = ref([])
const getOrders = async () => {
    const res = await getOrdersService(userStore.userInfo.userId)
    orders.value = res.data.data
    orders.value.forEach(order=>{
        let originPrice=0
        order.orderItems.forEach(item=>{
            originPrice+=item.price*item.goodsNum
        })
        order.originPrice=originPrice
    })
}
getOrders()

//取消订单
const cancelOrder = async (orderId) => {
    const res = await cancelOrderService(orderId);
    ElMessage.success(res.data.msg)
    getOrders()
}

//去支付
const toPay = async (orderId) => {
    router.push({
        path: '/pay',
        state: {
            orderId: orderId
        }
    })
}
</script>

<template>
    <div class="main">
        <el-card v-for="order in orders" class="order" shadow="never">
            <div class="orderInfo">
                <el-space wrap :size="20">
                    <el-text>下单时间：{{ order.orderTime }}</el-text>
                    <el-text>订单号：{{ order.number }}</el-text>
                </el-space>
                <el-text class="status">
                    <el-tag v-if="order.status === 1" type="warning">已下单</el-tag>
                    <el-tag v-else-if="order.status === 2" type="success">已支付</el-tag>
                    <el-tag v-else type="info">已取消</el-tag>
                </el-text>
            </div>
            <el-table :data="order.orderItems">
                <el-table-column width="400">
                    <template #default="scope">
                        <div class="goodsInfo">
                            <el-image :src="scope.row.picture"></el-image>
                            <el-text>{{ scope.row.description }}</el-text>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="price">
                    <template #default="scope">
                        <el-text>原价：￥{{ parseFloat(scope.row.price).toFixed(2) }}</el-text>
                    </template>
                </el-table-column>
                <el-table-column prop="payPrice">
                    <template #default="scope">
                        <el-text>支付价：￥{{ parseFloat(scope.row.payPrice).toFixed(2) }}</el-text>
                    </template>
                </el-table-column>
                <el-table-column prop="goodsNum">
                    <template #default="scope">
                        <el-text>数量：{{ scope.row.goodsNum }}</el-text>
                    </template>
                </el-table-column>
                <el-table-column fixed="right">
                    <template #default="scope">
                        <el-text>小计：￥{{ parseFloat(scope.row.payPrice * scope.row.goodsNum).toFixed(2) }}</el-text>
                    </template>
                </el-table-column>
            </el-table>
            <el-space class="pay" :size="13">
                <el-text>应付￥ {{ parseFloat(order.originPrice).toFixed(2) }} 元</el-text>
                <el-text>优惠￥ {{ parseFloat(order.originPrice-order.totalPrice).toFixed(2) }} 元</el-text>
                <el-text>实付￥ <span class="price">{{ parseFloat(order.totalPrice).toFixed(2) }}</span> 元</el-text>
                <el-button color="#ff5000" size="small" v-show="order.status === 1" @click="toPay(order.orderId)">去支付</el-button>
                <el-button color="#ff5000" size="small" plain v-show="order.status === 1" @click="cancelOrder(order.orderId)">取消订单</el-button>
            </el-space>
        </el-card>
    </div>
</template>

<style scoped>
.main {
    width: 80%;
    margin: 20px auto;
}

.el-card {
    margin: 10px 0px;
}

.orderInfo {
    display: flex;
    align-self: center;
}

.status {
    margin-left: auto;
}

.orderItem {
    display: flex;
    align-items: center;
    border: 0.5px solid rgba(124, 123, 123, 0.263);
    border-radius: 5px;
    padding: 20px;
    margin: 10px 0px;
}

.goodsInfo {
    display: flex;
}

.el-image {
    width: 80px;
    margin: auto 10px;
}

.price {
    font-size: larger;
    font-weight: bold;
}

.pay {
    display: flex;
    justify-content: right;
    margin-top: 10px;
}
</style>