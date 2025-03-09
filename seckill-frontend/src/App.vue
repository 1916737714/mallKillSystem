<script setup>
import zhCn from "element-plus/es/locale/lang/zh-cn"
</script>

<template>
  <el-config-provider :locale="zhCn">
    <router-view></router-view>
  </el-config-provider>
</template>

<style scoped>
:deep(.el-pagination.is-background .el-pager li:not(.is-disabled).is-active) {
  background-color: #ff5000 !important; 
}
:deep(.el-input) {
  --el-input-focus-border-color: #ff5000
}
:deep(.el-checkbox__input.is-checked .el-checkbox__inner),
:deep(.el-checkbox__input.is-indeterminate .el-checkbox__inner) {
	background: #ff5000;
	border: 1px solid #ff5000;
}

</style>
