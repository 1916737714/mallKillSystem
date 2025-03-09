<script setup>

const emit = defineEmits(['getList', 'reset', 'add'])
const getList = () => {
    emit('getList')
}
const reset = () => {
    emit('reset')
}
const add = () => {
    emit('add')
}

defineProps({
    addText: String
})
</script>

<template>
    <el-container>
        <el-header style="height: auto;">
            <slot name="queryBar"></slot>
            <div class="btnBar">
                <div class="leftBtnBar">
                    <el-button @click="getList" type="primary" size="small">查询</el-button>
                    <el-button @click="reset" type="primary" plain size="small">重置</el-button>
                </div>
                <div class="rightBtnBar">
                    <el-button @click="add" type="primary" size="small">{{ addText }}</el-button>
                </div>
            </div>
        </el-header>
        <el-main>
            <slot name="table"></slot>
        </el-main>
        <el-footer>
            <slot name="page"></slot>
        </el-footer>
    </el-container>

</template>

<style scoped>
.el-container {
    height: 100%;
}

.btnBar {
    display: flex;
    justify-content: space-between;
}

.el-main {
    height: 100%;
    margin: 10px 0;
}

.el-footer {
    height: auto;
    margin: 0 auto;
}
</style>