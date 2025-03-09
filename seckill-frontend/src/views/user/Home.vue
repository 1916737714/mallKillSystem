<!-- 首页 -->
<script setup>
import { getGoodsListService } from '@/api/goods';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { Check, FolderChecked, Location, Present, Right, Setting, Van } from '@element-plus/icons-vue';
import { getSeKillListService } from '@/api/seckill';
import banner1 from '@/assets/banner1.jpg'
import banner2 from '@/assets/banner2.jpg'
// import banner1 from '@/assets/banner1.png'
const router = useRouter()
//获取首页商品列表
const query = ref({
    currentPage: 1,
    pageSize: 10,
})
const goodsList = ref([])
const getGoodsList = async () => {
    const res = await getGoodsListService(query.value)
    goodsList.value = res.data.data
}
getGoodsList()

//获取首页秒杀活动列表
const secKillList = ref([])
const getSecKillList = async () => {
    const res = await getSeKillListService(query.value)
    secKillList.value = res.data.data
    length.value = secKillList.value.items.length
}
getSecKillList()

const row = ref(5)
const length = ref(0)

//跳转到详情页
const toGoodsDetail = (goodsId) => {
    router.push({
        path: `/detail/${goodsId}`,
        state: {
            isSecKill: false,
        }
    })
}
const toSecKillDetail = (secKillId) => {
    router.push({
        path: `/detail/${secKillId}`,
        state: {
            isSecKill: true,
        }
    })
}

const slides = ref([
    {
        id: 1,
        url: banner1
    },
    {
        id: 2,
        url: banner2
    },
    {
        id: 3,
        url: banner2
    },
])
</script>

<template>
    <div class="main">
        <el-carousel>
            <el-carousel-item v-for="slide in slides">
                <el-image :src="slide.url" fit="cover" class="carousel-image"></el-image>
            </el-carousel-item>
        </el-carousel>
        <div class="goods">
            <div class="textBar">
                <h4 class="seckill-title">限时秒杀</h4>
                <el-button link :icon="Right" @click="router.push('/seckill')">更多</el-button>
            </div>
            <div class="goodsList">
                <el-card v-for="secKill in secKillList.items" shadow="hover"
                    @click="toSecKillDetail(secKill.secKillId)">
                    <el-image :src="secKill.picture"></el-image>
                    <div><el-text>{{ secKill.description }}</el-text></div>
                    <div><el-text class="price">￥{{ parseFloat(secKill.secKillPrice).toFixed(2) }}/{{ secKill.unit }}</el-text></div>
                    <div><el-text tag="del">￥{{ parseFloat(secKill.price).toFixed(2) }}/{{ secKill.unit }}</el-text></div>
                </el-card>
                <el-card class="empty" v-for="item in (row - length % row)" v-if="length % row > 0"></el-card>
            </div>
        </div>
        <div class="goods">
            <div class="textBar">
                <h4>精选商品</h4>
                <el-button link :icon="Right" @click="router.push('/list')">更多</el-button>
            </div>
            <div class="goodsList">
                <el-card v-for="goods in goodsList.items" shadow="hover" @click="toGoodsDetail(goods.goodsId)">
                    <el-image :src="goods.picture"></el-image>
                    <div><el-text>{{ goods.description }}</el-text></div>
                    <div><el-text class="price">￥{{ parseFloat(goods.price).toFixed(2) }}/{{ goods.unit }}</el-text></div>
                </el-card>
            </div>
        </div> 
    </div>
    <div class="footer">
        <div class="services">
            <div class="service">
                <el-icon size="35" color="#909399">
                    <Setting />
                </el-icon>
                <el-text>维修服务</el-text>
            </div>
            <div class="service">
                <el-icon size="35" color="#909399">
                    <FolderChecked />
                </el-icon>
                <el-text>保险保障</el-text>
            </div>
            <div class="service">
                <el-icon size="35" color="#909399">
                    <Location />
                </el-icon>
                <el-text>线下门店</el-text>
            </div>
            <div class="service">
                <el-icon size="35" color="#909399">
                    <Van />
                </el-icon>
                <el-text>快速送达</el-text>
            </div>
            <div class="service">
                <el-icon size="35" color="#909399">
                    <Present />
                </el-icon>
                <el-text>精选好物</el-text>
            </div>
        </div>
        <div class="lines">
            <el-space class="line" spacer="-">
                <el-text>关于我们</el-text>
                <el-text>联系我们</el-text>
                <el-text>联系客服</el-text>
                <el-text>合作招商</el-text>
                <el-text>商家帮助</el-text>
                <el-text>营销中心</el-text>
                <el-text>友情链接</el-text>
                <el-text>销售联盟</el-text>
                <el-text>风险监测</el-text>
                <el-text>质量公告</el-text>
                <el-text>隐私政策</el-text>
            </el-space>
            <el-space class="line">
                <el-text>京公网安备11000002000088号</el-text>
                <el-text>京ICP备11041704号</el-text>
                <el-text>ICP</el-text>
                <el-text>药品医疗器械网络信息服务备案</el-text>
                <el-text>自营医疗器械经营资质</el-text>
                <el-text>药品网络交易第三方平台备案凭证</el-text>
                <el-text>新出发京零字第大120007号</el-text>
            </el-space>
            <el-space class="line">
                <el-text>互联网出版许可证编号新出网证(京)字150号</el-text>
                <el-text>出版物经营许可证</el-text>
                <el-text>违法和不良信息举报电话：4006561155</el-text>
            </el-space>
            <el-space class="line">
                <el-text>Copyright © 2004-2024</el-text>
                <el-text>消费者维权热线：4006067733</el-text>
                <el-text>经营证照</el-text>
                <el-text>营业执照</el-text>
                <el-text>增值电信业务经营许可证</el-text>
                <el-text>网络内容从业人员违法违规行为举报电话：4006561155-3</el-text>
            </el-space>
        </div>

    </div>
</template>

<style scoped>
.el-carousel {
    width: 100%;
    height: 280px;
    border-radius: 15px;
    object-fit: cover;
}



.main {
    padding: 0 20px;
}

.goodsList {
    display: flex;
    flex-wrap: wrap;
}

.el-card {
    width: 18%;
    /* height: 250px; */
    border-radius: 3px;
    margin: 10px auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
}

.price {
    font-size: medium;
    color: #ff5000;
}

.el-image {
    width: 80%;
    /* height: 180px; */
}

/* 父容器样式 */
.textBar {
    /* position: relative; 为了伪元素定位 */
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 10px;
}

/* 限时秒杀文字样式 */
.seckill-title {
    font-size: 24px; /* 设置字体大小 */
    font-weight: bold; /* 加粗 */
    background: linear-gradient(to right, #ff9999, #cc0000); /* 从浅红到深红的渐变 */
    -webkit-background-clip: text; /* 仅在文字上应用背景 */
    color: transparent; /* 让文字本身变透明，显示背景渐变 */
    position: relative; /* 为了定位伪元素 */
    overflow: hidden; /* 防止斜线超出文字范围 */
}

/* 动态斜线效果 */
.seckill-title::after {
    content: ''; /* 伪元素内容为空 */
    position: absolute;
    width: 150%; /* 让线足够长 */
    height: 2px; /* 线条的厚度 */
    background: red; /* 线条颜色 */
    top: 50%; /* 垂直居中 */
    left: -50%; /* 初始位置在文字左侧之外 */
    transform: rotate(-45deg); /* 让线条从左下角到右上角对角线斜切 */
    animation: seckill-line 1s ease-in-out infinite; /* 动画效果 */
}

/* 动态渐变动画（可选） */
@keyframes seckill-line {
    0% {
        left: -50%; /* 从文字左侧之外开始 */
    }
    100% {
        left: 100%; /* 滑过整个文字 */
    }
}

.empty {
    width: 18%;
    visibility: hidden
}

/* 底部 */
.footer {
    margin-top: 20px;
    padding: 20px;
    border-top: 1px solid rgba(159, 159, 159, 0.235);
    /* background-color: antiquewhite; */
}

.services {
    display: flex;
    justify-self: center;
    margin-bottom: 20px;
}

.service {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 200px;
    height: 50px;
}

.service .el-text {
    font-size: large;
    margin-left: 10px;
}

.line {
    margin-bottom: 8px;
    display: flex;
    justify-self: center;
}
</style>