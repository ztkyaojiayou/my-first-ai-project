<template>
  <div class="dashboard">
    <div class="welcome">
      <h1>欢迎回来，{{ userStore.userInfo?.nickname || userStore.userInfo?.username }}！</h1>
      <p>今天也要元气满满呀 🎉</p>
    </div>

    <div class="stats-cards">
      <a-row :gutter="24">
        <a-col :span="8">
          <a-card class="stat-card">
            <div class="stat-icon" style="background: #e6f7ff;">
              <UserOutlined style="color: #1890ff; font-size: 28px;" />
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ userCount }}</div>
              <div class="stat-label">用户总数</div>
            </div>
          </a-card>
        </a-col>
        <a-col :span="8">
          <a-card class="stat-card">
            <div class="stat-icon" style="background: #f6ffed;">
              <CheckCircleOutlined style="color: #52c41a; font-size: 28px;" />
            </div>
            <div class="stat-content">
              <div class="stat-value">—</div>
              <div class="stat-label">活跃用户</div>
            </div>
          </a-card>
        </a-col>
        <a-col :span="8">
          <a-card class="stat-card">
            <div class="stat-icon" style="background: #fff7e6;">
              <CalendarOutlined style="color: #fa8c16; font-size: 28px;" />
            </div>
            <div class="stat-content">
              <div class="stat-value">{{ currentDate }}</div>
              <div class="stat-label">当前日期</div>
            </div>
          </a-card>
        </a-col>
      </a-row>
    </div>

    <div class="quick-actions">
      <a-card title="快捷操作">
        <a-space wrap>
          <a-button type="primary" @click="$router.push('/users')">
            <TeamOutlined /> 用户管理
          </a-button>
          <a-button @click="$router.push('/profile')">
            <UserOutlined /> 个人中心
          </a-button>
        </a-space>
      </a-card>
    </div>
  </div>
</template>

<script setup lang="ts">
/**
 * 控制台页面
 * 功能：显示欢迎信息、数据统计卡片
 */
import { ref, onMounted } from 'vue'
import { UserOutlined, CheckCircleOutlined, CalendarOutlined, TeamOutlined } from '@ant-design/icons-vue'
import { useUserStore } from '@/store/user'
import dayjs from 'dayjs'
import { userApi } from '@/api'

const userStore = useUserStore()
const userCount = ref(0)
const currentDate = ref(dayjs().format('YYYY-MM-DD'))

// 页面加载
onMounted(async () => {
  // 尝试获取用户列表来统计数量
  try {
    const res = await userApi.getList({ pageNum: 1, pageSize: 100 })
    userCount.value = res.total || 0
  } catch (e) {
    // 无权限则显示 0
    userCount.value = 0
  }
})
</script>

<style scoped>
.dashboard {
  padding: 0;
}

.welcome {
  margin-bottom: 24px;
}

.welcome h1 {
  font-size: 28px;
  color: #333;
  margin-bottom: 8px;
}

.welcome p {
  color: #666;
  font-size: 16px;
}

.stats-cards {
  margin-bottom: 24px;
}

.stat-card {
  display: flex;
  align-items: center;
  gap: 16px;
}

.stat-icon {
  width: 64px;
  height: 64px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #333;
}

.stat-label {
  color: #999;
  font-size: 14px;
}
</style>
