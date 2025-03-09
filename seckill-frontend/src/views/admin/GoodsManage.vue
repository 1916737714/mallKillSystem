<script setup>
import { ref } from 'vue';
// import { ElMessageBox } from 'element-plus';
import PageContainer from '@/components/PageContainer.vue';
import SecKillEdit from './SecKillEdit.vue';
import { delSecKillService, getSeKillListService } from '@/api/seckill';
//查询
const query = ref({
    currentPage: 1,
    pageSize: 20,
    key: '',
    status: ''
})

const secKillList = ref([])
const getSecKillList = async () => {
    const res = await getSeKillListService(query.value)
    secKillList.value = res.data.data
    secKillList.value.items = secKillList.value.items.map(item => {
        let status
        const nowTime = ref(new Date())
        const startTime = ref(new Date(item.startTime))
        const endTime = ref(new Date(item.endTime))
        if (startTime.value >= nowTime.value) {
            status = 1 //未开始
        } else if (endTime.value <= nowTime.value) {
            status = 0
        } else if (startTime.value < nowTime.value && endTime.value > nowTime.value) {
            status = 2
        }
        return { ...item, status: status }
    })
    console.log(secKillList.value)
}
getSecKillList()

//分页
const curChange = (current) => {
    query.value.currentPage = current
    getSecKillList()
}

//重置
const resetForm = () => {
    for (let key in query.value) {
        query.value[key] = ''
    }
    query.value.currentPage = 1
    query.value.pageSize = 20
    query.value.key = null
    query.value.status = null
    getSecKillList()
}

//添加/编辑抽屉
const drawer = ref()
const openEditDrawer = (row) => {
    drawer.value.openDrawer(row)
}

//删除
const openDelBox = (row) => {
    ElMessageBox.confirm(
        '秒杀活动删除后不可恢复，确认删除该秒杀活动吗？',
        '确认删除',
        {
            confirmButtonText: '删除',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            await delSecKillService(row.secKillId)
            getSecKillList()
            ElMessage.success('删除成功')
        })
        .catch(() => {
        })
}
</script>

<template>
    <page-container :addText="'添加秒杀活动'" @getList="getSecKillList" @reset="resetForm" @add="openEditDrawer(null)">
        <template #queryBar>
            <el-form :inline="true" class="demo-form-inline" label-width="auto" label-position="left">
                <el-form-item label="商品描述">
                    <el-input v-model="query.key" placeholder="请输入商品描述" clearable></el-input>
                </el-form-item>
            </el-form>
        </template>
        <template #table>
            <el-scrollbar>
                <el-table :data="secKillList.items" style="width: 100%">
                    <el-table-column label="商品图片" width="80">
                        <template #default="{ row }">
                            <el-image :src="row.picture" fit="fill" />
                        </template>
                    </el-table-column>
                    <el-table-column label="商品描述" prop="description" width="220" />
                    <el-table-column label="商品价格" prop="secKillPrice" width="120">
                        <template #default="{ row }">
                            <div class="price">
                                <div><el-text>{{ row.secKillPrice }}元/{{ row.unit }}</el-text></div>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="商品库存" prop="secKillStock" width="80" />
                    
    
                    <el-table-column  width="240" />
    
                    <el-table-column label="操作" fixed="right" min-width="120">
                        <template #default="{ row }">
                            <el-button @click="openDelBox(row)" link type="primary">删除</el-button>
                            <el-button @click="openEditDrawer(row)" link type="primary">编辑</el-button>
                        </template>
                    </el-table-column>{ path: '/admin/seckill', component: () => import('@/views/admin/SecKillManage.vue') },
                </el-table>
            </el-scrollbar>
        </template>
        <template #page>
            <pagination :total="secKillList.total" :currentPage="query.currentPage" :pageSize="query.pageSize"
                @curChange="curChange" />
        </template>
    </page-container>

    <sec-kill-edit ref="drawer" @success="getSecKillList"></sec-kill-edit>

</template>
<style scoped>
.demo-form-inline .el-input {
    --el-input-width: 200px;
}

.demo-form-inline .el-select {
    --el-select-width: 200px;
}

.demo-form-inline .price {
    --el-input-width: 90px;
}

.demo-form-inline .center {
    margin: 0 6px;
}
</style>