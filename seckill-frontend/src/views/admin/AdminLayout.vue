<script setup>
import zhCn from "element-plus/es/locale/lang/zh-cn"
import { UserFilled, Shop, GoodsFilled, Menu, Sell, Setting, Upload } from "@element-plus/icons-vue"
// import { ElMessageBox } from "element-plus";
import { useTokenStore } from "@/stores/token";
import { useUserStore } from "@/stores/user";
import { useRouter } from "vue-router";
const userStore = useUserStore()
const tokenStore = useTokenStore()
const router=useRouter()
const logout = () => {
    ElMessageBox.confirm('确定要退出登陆吗？', '', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    })
        .then(() => {
            userStore.clearUserInfo()
            tokenStore.removeToken()
            router.push('/')
        })
        .catch(() => { })
}
</script>

<template>
    <!-- 全局配置中文 -->
    <el-config-provider :locale="zhCn">
        <el-container>
            <el-aside>
                <el-menu :default-active="$route.path" router background-color="#444A5A" text-color="white">
                    <div>
                        <div class="adminInfo">
                            <el-avatar :icon="UserFilled" v-if="userStore.userInfo.avatar == null" />
                            <el-image :src="userStore.userInfo.avatar" v-else></el-image>
                            <span class="adminUsername">{{ userStore.userInfo.username }}</span>
                        </div>
                        <el-menu-item index="/admin/seckill">
                            <el-icon>
                                <GoodsFilled />
                            </el-icon>
                            <span>秒杀活动管理</span>
                        </el-menu-item>
                        
                        <el-menu-item index="/admin/goods">
                            <el-icon>
                                <Menu />
                            </el-icon>
                            <span>商品管理</span>
                        </el-menu-item>

                    </div>
                    <el-menu-item @click="logout" class="logout">
                        <el-icon class="logoutBtn">
                            <Upload />
                        </el-icon>
                        <span>退出登录</span>
                    </el-menu-item>
                </el-menu>
            </el-aside>
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-config-provider>
</template>

<style scoped>
.el-container {
    height: 100vh;
    background-color: white
}

.el-aside {
    width: 200px;
}

.el-menu,
.el-main {
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.adminInfo {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-top: 20px;
    padding-bottom: 20px;
}

.adminUsername {
    margin-top: 10px;
    color: white;
}

.logoutBtn {
    transform: rotate(90deg);
}
</style>