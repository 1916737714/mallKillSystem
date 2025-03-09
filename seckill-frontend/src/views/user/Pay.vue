<!-- 确认订单支付页 -->
<script setup>
import { removeFromCartService } from '@/api/cart';
import { addOrderService, addSecKillOrderService, getOrderDetailService, payService } from '@/api/order';
import { useCartStore } from '@/stores/cart';
import { useUserStore } from '@/stores/user';
import { ElMessage } from 'element-plus';
import { nextTick, ref,watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import aliPay from '@/assets/alipay.png'
import card from '@/assets/card.png'
import wechat from '@/assets/wechat.png'
const route=useRoute()
const router=useRouter()
const cartStore=useCartStore()
const userStore=useUserStore()
const order = ref([])
const orderItems=ref([])

//获取订单详情
const getOrderDetail=async(orderId)=>{
    const res=await getOrderDetailService(orderId)
    order.value=res.data.data
    orderItems.value=order.value.orderItems
}

//计算应付和实付
const originPrice = ref(0)
const actualPrice=ref(0) 
const calc= ()=> {
    orderItems.value.forEach(item => {
        originPrice.value += item.price * item.goodsNum
    });
    orderItems.value.forEach(item=>{
        actualPrice.value+=item.payPrice*item.goodsNum
    })
}

//根据路由处理数据
const backPath=history.state.back
const handleByPath=async()=>{
    if(backPath.includes('/order')){
        await getOrderDetail(history.state.orderId)
    }else if(backPath.includes('/detail')){
        const goodsInfo=ref({...history.state.goodsInfo})
        const isSecKill=goodsInfo.value.isSecKill
        const payPrice=isSecKill?goodsInfo.value.secKillPrice:goodsInfo.value.price
        const orderItem=ref({
            ...goodsInfo.value,
            payPrice:payPrice,
            isSecKill:isSecKill?goodsInfo.value.secKillId:null
        })
        orderItems.value.push(orderItem.value)
    }else if(backPath.includes('/cart')){
        const carts = JSON.parse(history.state.carts);    
        for (const key in carts) {
            if (carts.hasOwnProperty(key)) {
                orderItems.value.push({
                    cartId:carts[key].cartId,
                    ...carts[key].goods,
                    goodsNum: carts[key].goodsNum,
                    payPrice: carts[key].goods.price,
                    isSecKill:null
                });
            }
        }
    }
    calc()
}
handleByPath()

//收货信息
const ruleRef=ref()
const rules={
    tel: [{ required: true, message: '请输入收件人联系方式', trigger: 'change' }],
    username: [{ required: true, message: '请输入收件人昵称', trigger: 'change' }],
    addr: [{ required: true, message: '请输入收件人地址', trigger: 'change' }],
    payMethod: [{ required: true, message: '请选择支付方式', trigger: 'change' }]
}
 
const receiveForm=ref({
    username:userStore.userInfo.username,
    tel:userStore.userInfo.tel,
    addr:'',
    payMethod:1
})

//取消支付
const dialogVisible=ref(false)
const cancelPay=async()=>{
    if(order.value.orderId){
        await getOrderDetail(order.value.orderId)
    }    
    dialogVisible.value=false
}

//创建订单
const createOrder=async()=>{
    await ruleRef.value.validate()
    dialogVisible.value=true
    if(order.value.orderId){
        return 
    }
    let orderRes
    //从购物车和详情页的需要创建订单，订单页不需要
    if(backPath.includes('/detail')){
        if(orderItems.value[0].isSecKill){
            orderRes=await addSecKillOrderService(orderItems.value[0].isSecKill,orderItems.value[0].goodsNum)
        }else{
            orderRes=await addOrderService(orderItems.value)
        }
    }else if(backPath.includes('/cart')){
        orderRes=await addOrderService(orderItems.value)
        for(const key in orderItems.value){
            await removeFromCartService(orderItems.value[key].cartId)            
        }
    }
    order.value=orderRes.data.data
    orderItems.value=order.value.orderItems
}

//点击立即付款/已下单状态改为已支付
const paySuccess=ref(false)
const pay=async()=>{
    const res=await payService(order.value.orderId)
    paySuccess.value=true
}

const payMethod=[
    {
        id:1,
        text:'支付宝',
        icon:aliPay,
        value:1
    },
    {
        id:2,
        text:'微信',
        icon:wechat,
        value:2
    },
    {
        id:3,
        text:'银行卡',
        icon:card,
        value:3
    },
]
const radioChange=(value)=>{
    receiveForm.value.payMethod=value
}

const radio = ref(1)
</script>

<template>
    <div class="main">
        <el-card class="left" shadow="never">
            <h4>订单详情</h4>
            <el-scrollbar height="300px">
                <div v-for="orderItem in orderItems" class="orderItemInfo">
                    <el-image class="pic" :src="orderItem.picture"></el-image>
                    <div class="desc">
                        <div><el-text>{{ orderItem.description }}</el-text></div>
                        <div>
                            <el-text class="price">￥{{ parseFloat(orderItem.payPrice).toFixed(2) }}/{{ orderItem.unit }} </el-text>
                            <el-text tag="del" size="small" v-if="orderItem.price != orderItem.payPrice">
                                ￥{{ parseFloat(orderItem.price).toFixed(2) }}
                            </el-text>
                        </div>
                    </div>
                    <div class="calcPrice">
                        <el-text>x {{ orderItem.goodsNum }}</el-text>
                        <el-text>￥{{ parseFloat(orderItem.payPrice * orderItem.goodsNum).toFixed(2) }}</el-text>
                    </div>
                </div>
            </el-scrollbar>
            <div class="bottom">
                <div class="bottomItem">
                    <el-text>应付</el-text>
                    <el-text>￥{{ parseFloat(originPrice).toFixed(2) }}</el-text>
                </div>
                <div class="bottomItem">
                    <el-text>优惠</el-text>
                    <el-text>￥{{ parseFloat(originPrice - actualPrice).toFixed(2) }}</el-text>
                </div>
                <div class="bottomItem">
                    <el-text>实付</el-text>
                    <el-text class="price" size="large">￥{{ parseFloat(actualPrice).toFixed(2) }}</el-text>
                </div>
                <div class="bottomItem" v-if="order.orderId">
                    <el-text>订单号</el-text>
                    <el-text>{{ order.number }}</el-text>
                </div>
                <div class="bottomItem" v-if="order.orderId">
                    <el-text>下单时间</el-text>
                    <el-text>{{ order.orderTime }}</el-text>
                </div>
            </div>
        </el-card>
        <el-card class="right" shadow="never">
            <el-form :model="receiveForm" label-position="top" :rules="rules" ref="ruleRef">
                <el-form-item prop="payMethod">
                    <h4>支付方式</h4>
                    <el-radio-group v-model="radio" class="payMethod" @change="radioChange">
                        <el-radio v-model="receiveForm.payMethod" v-for="item in payMethod" :value="item.value" border >
                            <div class="payRadio">
                                <el-image class="payIcon" :src="item.icon"></el-image>
                                <el-text>{{ item.text }}</el-text>
                            </div>
                        </el-radio>
                    </el-radio-group>
                </el-form-item>
                <h4>收件信息</h4>
                <el-form-item label="收件人昵称" prop="username">
                    <el-input v-model="receiveForm.username" placeholder="请输入昵称" clearable />
                </el-form-item>
                <el-form-item label="收件人联系方式" prop="tel">
                    <el-input v-model="receiveForm.tel" placeholder="请输入联系方式" clearable />
                </el-form-item>
                <el-form-item label="收件人地址" prop="addr">
                    <el-input v-model="receiveForm.addr" placeholder="请输入地址" clearable />
                </el-form-item>
            </el-form>
            <el-button type="primary" color="#ff5000" class="payBtn" @click="createOrder" v-if="order.status===1||!paySuccess">立即支付</el-button>
        </el-card>
    </div>
    <el-dialog v-model="dialogVisible" title="确认支付" :show-close="!paySuccess"
    :before-close="cancelPay" width="300" modal="true" class="dialog">
        <!-- <el-loading v-if="!paySuccess"></el-loading> -->
        <el-result v-if="paySuccess" icon="success" title="支付成功">
            <template #extra>
                <el-button link @click="router.push('/home')">返回首页</el-button>
            </template>
        </el-result>
        <el-text class="actualPrice" v-if="!paySuccess">￥{{ parseFloat(actualPrice).toFixed(2) }}元</el-text>
        <template #footer v-if="!paySuccess">
            <div class="dialog-footer">
                <el-button type="primary" color="#ff5000" class="confirmBtn" @click="pay">确认支付</el-button>
            </div>
        </template>
    </el-dialog>
</template>

<style scoped>
.main {
    width: 80%;
    height: calc(100vh - 60px - 40px);
    margin: auto;
    display: flex;
    align-items: center;
}

.left {
    width: 55%;
    height: 100%;
}
.el-radio-group {
    display: flex;
    justify-content: space-around;
}
.el-radio {
    width: auto;
    height: auto;
}

.payRadio {
    display: flex;
    align-items: center;
    padding: 0 5px;
}
.payRadio .el-image {
    display: flex;
    align-self: center;
}

:deep(.el-radio__input) {
    display: none;
}

:deep(.el-radio__label) {
    padding-left: 0;
}

.payIcon {
    width: 25px;
    margin: 8px 5px 8px 0px;
}

.right {
    margin-left: auto;
    width: 42%;
    height: 100%;
}

.el-scrollbar {
    border-top: 1px dotted rgba(128, 128, 128, 0.358);
    border-bottom: 1px dotted rgba(128, 128, 128, 0.358);
}

.orderItemInfo {
    display: flex;
    align-items: center;
    margin: 10px 0px;
}

.pic {
    width: 18%;
}

.desc {
    width: 50%;
    margin: 0 auto 0 10px;
}

.price {
    color: #ff5000;
}

.calcPrice {
    display: flex;
    flex-direction: column;
    justify-self: right;
    margin-right: 25px;
    width: 20%;
}
.calcPrice .el-text{
    margin-left: auto;
    margin-bottom: 10px;
}

.bottom {
    display: flex;
    flex-direction: column;
    padding: 20px 10px;
}
.bottomItem {
    display: flex;
    justify-content: space-between;
}
.bottomItem .el-text {
    margin-bottom: 7px;
}
:deep(.el-dialog__body) {
    height: 100vh;
}
.actualPrice {
    font-size:30px;
    display: flex;
    justify-self: center;
    margin: 10px 0;
    color: #ff5000;
}
.confirmBtn,.payBtn {
    width: 100%;
    margin-top: 10px;
}
</style>