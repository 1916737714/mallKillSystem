<!-- 用户注册/登录页 -->
<script setup>
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { Iphone,Lock,User } from '@element-plus/icons-vue'
import { userLoginService, userRegisterService,getUserInfoService } from '@/api/user';
import { useTokenStore } from '@/stores/token';
import { ElMessage } from 'element-plus';
import { useUserStore } from '@/stores/user';
const userStore=useUserStore()
const tokenStore=useTokenStore()
const router=useRouter()

//表单
const userForm = ref({
    tel: '',
    username: '',
    password: ''
})

//登录/注册切换
const isLogin = ref(true)
watch(isLogin, () => {
    userForm.value = {
        tel: '',
        username: '',
        password: ''
    }
})

//表单校验
const ruleRef = ref()
const rules = {
    tel: [{ required: true, message: '请输入手机号', trigger: 'change' }],
    username: [{ required: true, message: '请输入用户名', trigger: 'change' }],
    password: [{ required: true, message: '请输入密码', trigger: 'change' }]
}

//登录
const login = async () => {
    await ruleRef.value.validate()
    const res=await userLoginService({
        tel:userForm.value.tel,
        password:userForm.value.password
    })
    tokenStore.setToken(res.data.data)
    //获取用户信息
    const userRes=await getUserInfoService()
    userStore.setUserInfo(userRes.data.data)
    if(userStore.userInfo.role===1){
        router.push("/admin")
    }else{
        router.push("/home")
    }
}

//注册
const register = async () => {
    await ruleRef.value.validate()
    const res=await userRegisterService(userForm.value)
    ElMessage.success(res.data.msg)
    isLogin.value=true
}

</script>

<template>
    <!-- 登录 -->
    <div class="bg">
        <el-card>
            <el-form :model="userForm" v-if="isLogin" ref="ruleRef" :rules="rules" label-width="auto">
                <h3>登录</h3>
                <el-form-item prop="tel">
                    <el-input v-model="userForm.tel" placeholder="请输入手机号">
                        <template #prefix>
                            <el-icon><Iphone /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="userForm.password" placeholder="请输入密码" type="password" show-password>
                        <template #prefix>
                            <el-icon><Lock /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-button @click="login" type="primary" color="#ff5000">登录</el-button>
                <el-button link @click="isLogin = false" class="tip">尚无账号？去注册→</el-button>
            </el-form>

            <!-- 注册 -->
            <el-form :model="userForm" v-else ref="ruleRef" :rules="rules" label-width="auto" label-position="left">
                <h3>注册</h3>
                <el-form-item prop="tel">
                    <el-input v-model="userForm.tel" placeholder="请输入手机号">
                        <template #prefix>
                            <el-icon><Iphone /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input v-model="userForm.username" placeholder="请输入用户名">
                        <template #prefix>
                            <el-icon><User /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="userForm.password" placeholder="请输入密码" type="password" show-password>
                        <template #prefix>
                            <el-icon><Lock /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-button @click="register" type="primary" color="#ff5000">注册</el-button>
                <el-button link @click="isLogin = true" class="tip">已有账号？去登录→</el-button>
            </el-form>
        </el-card>
    </div>
</template>

<style scoped>
.bg {
    display: flex;
    justify-content: center; 
    align-items: center; 
    height: 100vh; 
}
.el-card {
    width: 400px;
    /* margin: 0 auto; */
    padding: 0px 30px 40px 30px;
    display: flex;
    flex-direction: column;
}

.el-form {
    margin: 0px 15px;
}

.el-button {
    width: 100%;
    margin-left: 0;
}

.tip {
    margin: 20px 0px;
}
</style>