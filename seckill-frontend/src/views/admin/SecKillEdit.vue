<script setup>
import { getGoodsListService } from '@/api/goods';
import { addSecKillService, updateSecKillIngService, updateSecKillService } from '@/api/seckill';
import { ElMessage } from 'element-plus';
import { ref } from 'vue';
var secKillForm = ref({
    goodsId: '',
    secKillPrice: '',
    limitNum: '',
    secKillStock: '',
    startTime: '',
    endTime: ''
})
const drawerVisible = ref(false)
const status = ref()
const openDrawer = (row) => {
    setTimeout(() => {
        formRef.value.clearValidate() //先清除校验
    }, 0)
    drawerVisible.value = true
    secKillForm.value = { ...row }
    status.value = secKillForm.value.secKillId ? secKillForm.value.status : -1
}

const secKillRules = {
    goodsId: [{ required: true, message: '请选择商品', trigger: 'change' }],
    secKillPrice: [
        { required: true, message: '请输入秒杀活动价格', trigger: 'change' },
        { pattern: /^(([1-9]{1}\d{0,9})|(0{1}))(\.\d{1,2})?$/, message: '请输入正确的金额格式，可保留两位小数' },
    ],
    limitNum: [
        { required: true, message: '请输入限购数', trigger: 'change' },
    ],
    secKillStock: [
        { required: true, message: '请输入秒杀活动库存', trigger: 'change' },
    ],
    startTime: [
        { required: true, message: '请选择开始时间', trigger: 'change' },
    ],
    endTime: [
        { required: true, message: '请选择结束时间', trigger: 'change' },
    ],
}
const formRef = ref()

//远程搜索商品
const goodsList = ref([])
const goodsQuery = ref({
    currentPage: 1,
    pageSize: 100,
    key: ''
})
const getGoodsRemote = async (key) => {
    goodsQuery.value.key = key
    const res = await getGoodsListService(goodsQuery.value)
    goodsList.value = res.data.data
}

const emit = defineEmits(['success'])
const submit = async () => {
    await formRef.value.validate()
    const isEdit = secKillForm.value.secKillId //判断编辑/添加
    if (isEdit) {
        if (status.value === 1) {
            //未开始
            const res = await updateSecKillService(secKillForm.value)
            ElMessage.success(res.data.msg)
        } else if (status.value === 2) {
            //进行中只能修改库存
            const res = await updateSecKillIngService(secKillForm.value.secKillId, secKillForm.value.secKillStock)
            ElMessage.success(res.data.msg)
        }
    } else {
        const res = await addSecKillService(secKillForm.value) //添加
        ElMessage.success(res.date.msg)
    }
    drawerVisible.value = false
    emit('success')
}

defineExpose({
    openDrawer
})
</script>

<template>
    <el-drawer v-model="drawerVisible" direction="rtl" size="500">
        <template #header="{ close, titleId, titleClass }">
            <h4 :id="titleId" :class="titleClass">{{ secKillForm.secKillId ? '编辑秒杀活动' : '添加秒杀活动' }} </h4>
            <el-tag type="primary" v-if="status === 1">该秒杀活动未开始</el-tag>
            <el-tag type="warning" v-else-if="status === 2">该秒杀活动进行中</el-tag>
            <el-tag type="info" v-else-if="status === 0">该秒杀活动已结束</el-tag>
        </template>
        <el-form :model="secKillForm" :rules="secKillRules" ref="formRef" label-position="top" :disabled="status === 3">
            <el-form-item label="商品" prop="goodsId" v-if="status === -1">
                <el-select v-model="secKillForm.goodsId" placeholder="请输入商品名" filterable remote
                    :remote-method="getGoodsRemote">
                    <el-option v-for="goods in goodsList.items" :key="goods.goodsId" :label="goods.description"
                        :value="goods.goodsId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="商品" v-if="status != -1">
                <el-input v-model="secKillForm.description" :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="秒杀价格" prop="secKillPrice">
                <el-input v-model="secKillForm.secKillPrice" placeholder="请输入秒杀活动价格"
                    :disabled="status === 0 || status === 2">
                    <template #prepend>￥</template>
                    <template #append>元</template>
                </el-input>
            </el-form-item>
            <el-form-item label="秒杀库存" prop="secKillStock">
                <el-input v-model="secKillForm.secKillStock" placeholder="请输入秒杀活动库存" />
            </el-form-item>
            <el-form-item label="限购数" prop="limitNum">
                <el-input v-model="secKillForm.limitNum" placeholder="请输入限购数"
                    :disabled="status === 0 || status === 2" />
            </el-form-item>
            <el-space wrap>
                <el-form-item label="开始时间" prop="startTime">
                    <el-date-picker v-model="secKillForm.startTime" type="datetime" placeholder="请选择开始时间"
                        class="startTime" value-format="YYYY-MM-DD HH:mm:ss" :disabled="status === 0 || status === 2" />
                </el-form-item>
                <el-form-item label="结束时间" prop="endTime">
                    <el-date-picker v-model="secKillForm.endTime" type="datetime" placeholder="请选择结束时间" class="endTime"
                        value-format="YYYY-MM-DD HH:mm:ss" :disabled="status === 0 || status === 2" />
                </el-form-item>
            </el-space>
        </el-form>
        <template #footer>
            <div style="flex: auto">
                <el-button @click="drawerVisible = false">取消</el-button>
                <el-button @click="submit" type="primary">确认</el-button>
            </div>
        </template>
    </el-drawer>
</template>

<style scoped>
.logo {
    width: 20px;
    height: 20px;
    margin-right: 5px;
    border-radius: 50%
}

.picture {
    width: 20px;
    height: 20px;
    margin-right: 5px;
    border-radius: 5px
}
</style>