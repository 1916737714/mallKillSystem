<!-- 购物车页 -->
<script setup>
import { useRouter } from 'vue-router';
import { Delete, Plus, Minus } from '@element-plus/icons-vue'
import { ref } from 'vue';
import { removeFromCartService, updateNumService } from '@/api/cart';
import { useUserStore } from '@/stores/user';
import { useCartStore } from '@/stores/cart';
const router = useRouter()
const userStore = useUserStore()
const cartStore = useCartStore()
const rowKey=(row)=>{
    return row.cartId
}
const cartList = ref([])
//获取购物车列表
const getCartList = async() => {
    await cartStore.getCartList()
    cartList.value=cartStore.cartList
}
getCartList()

//修改购物车商品数量
const handleNumChange = async(row, change) => {
    // row.goodsNum += change
    await cartStore.updateNum({
        cartId: row.cartId,
        goodsId: row.goodsId,
        goodsNum: row.goodsNum+change
    })
    getCartList()
}

//移出购物车
const removeFromCart = async(cartId) => {
    const res = await removeFromCartService(cartId)
    getCartList()
}

//支付
const goToPay = () => {
    router.push({
        path:'/pay',
        state:{
            // carts:{...selectedCart.value}
            carts:JSON.stringify(selectedCart.value)
        }
    })
}

//底部统计栏
const selectedNum = ref(0)
const totalPrice = ref(0)
const selectedCart=ref([])
const checkChange = (rows) => {
    // console.log(rows)
    // cartStore.setSelectedCart(rows)
    selectedCart.value={...rows}
    totalPrice.value = 0
    rows.forEach(item => {
        totalPrice.value += item.goods.price * item.goodsNum
    });
    selectedNum.value = rows.length
}

</script>

<template>
    <el-container>
        <el-main>
            <el-table :data="cartList" table-layout="auto" @selection-change="checkChange">
                <el-table-column type="selection" width="10">
                </el-table-column>
                <el-table-column label="商品信息"  width="350">
                    <template #default="scope">
                        <div class="goodsInfo">
                            <el-image :src="scope.row.goods.picture"></el-image>
                            <el-text>{{ scope.row.goods.description }}</el-text>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="单价" prop="price">
                    <template #default="scope">
                        <el-text>￥{{ parseFloat(scope.row.goods.price).toFixed(2) }}</el-text>
                    </template>
                </el-table-column>
                <el-table-column label="数量" prop="num">
                    <template #default="scope">
                        <el-input v-model="scope.row.goodsNum" class="counter">
                            <template #prefix>
                                <el-button @click="handleNumChange(scope.row, -1)" link :icon="Minus"
                                    :disabled="scope.row.goodsNum === 1"></el-button>
                            </template>
                            <template #suffix>
                                <el-button @click="handleNumChange(scope.row, 1)" link :icon="Plus"
                                    :disabled="scope.row.goodsNum === scope.row.goods.stock"></el-button>
                            </template>
                        </el-input>
                    </template>
                </el-table-column>
                <el-table-column label="小计">
                    <template #default="scope">
                        <el-text>￥{{ parseFloat(scope.row.goods.price * scope.row.goodsNum).toFixed(2) }}</el-text>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template #default="scope">
                        <el-button link :icon="Delete" @click="removeFromCart(scope.row.cartId)">移出购物车</el-button>
                    </template>
                </el-table-column>
            </el-table>

        </el-main>
        <el-footer>
            <el-text>已选 <span class="totalText">{{ selectedNum }}</span> 件商品</el-text>
            <el-text>总价：<span class="totalText">￥{{ parseFloat(totalPrice).toFixed(2) }}</span></el-text>
            <el-text><el-button color="#ff5000" @click="goToPay" :disabled="selectedNum===0">去结算</el-button></el-text>
        </el-footer>
    </el-container>
</template>

<style scoped>
.el-main {
    width: 100%;
    margin-bottom: 80px;
}

.goodsInfo {
    display: flex;
}

.el-table {
    width: 80%;
    margin: 0 auto;
}

.el-image {
    width: 100px;
    margin: auto 20px auto 0px;
}

.counter {
    width: 120px;
}

:deep(.counter .el-input__inner) {
    text-align: center;
}


.el-footer {
    position: fixed;
    bottom: 0;
    width: 100%;
    height: 80px;
    z-index: 100;
    padding: 0 150px;
    display: flex;
    justify-content: end;
    background-color: #fff;
    box-shadow: 0 0 2px #0000004b;
}

.el-footer .el-text {
    margin-right: 20px;
}

.el-footer .el-button {
    width: 100px;
}

.el-footer .totalText {
    font-size: x-large;
    font-weight: bolder;
    color: #ff5000;
}
</style>