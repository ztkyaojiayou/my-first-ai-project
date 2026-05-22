<template>
  <div class="layout-container">
    <!-- 顶部导航栏 -->
    <a-layout-header class="header">
      <div class="logo">👤 用户管理系统</div>
      <div class="right-menu">
        <a-dropdown>
          <template #overlay>
            <a-menu>
              <a-menu-item key="profile" @click="$router.push('/profile')">
                <UserOutlined /> 个人中心
              </a-menu-item>
              <a-menu-item key="logout" @click="handleLogout">
                <LogoutOutlined /> 退出登录
              </a-menu-item>
            </a-menu>
          </template>
          <span class="user-info">
            <UserOutlined /> {{ userStore.userInfo?.nickname || userStore.userInfo?.username }}
          </span>
        </a-dropdown>
      </div>
    </a-layout-header>

    <!-- 内容区域 -->
    <a-layout class="main-layout">
      <!-- 左侧菜单 -->
      <a-layout-sider width="220">
        <a-menu
          v-model:selectedKeys="selectedKeys"
          mode="inline"
          theme="light"
        >
          <a-menu-item key="/dashboard" @click="$router.push('/dashboard')">
            <DashboardOutlined />
            <span>控制台</span>
          </a-menu-item>
          <a-menu-item key="/users" @click="$router.push('/users')">
            <TeamOutlined />
            <span>用户管理</span>
          </a-menu-item>
          <a-menu-item key="/profile" @click="$router.push('/profile')">
            <UserOutlined />
            <span>个人中心</span>
          </a-menu-item>
        </a-menu>
      </a-layout-sider>

      <!-- 右侧内容区 -->
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script setup lang="ts">
/**
 * 主布局组件
 * 功能：包含导航栏、侧边菜单、内容区
 */
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { DashboardOutlined, TeamOutlined, UserOutlined, LogoutOutlined } from '@ant-design/icons-vue'
import { useUserStore } from '@/store/user'
import { message } from 'ant-design-vue'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const selectedKeys = ref([route.path])

// 页面加载时获取用户信息
onMounted(async () => {
  if (!userStore.userInfo) {
    await userStore.getUserInfo()
  }
})

// 退出登录
async function handleLogout() {
  await userStore.logout()
  message.success('已退出登录')
  router.push('/login')
}
</script>

<style scoped>
.layout-container {
  min-height: 100vh;
}

.header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 24px;
}

.logo {
  color: white;
  font-size: 20px;
  font-weight: bold;
}

.right-menu {
  color: white;
  cursor: pointer;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.main-layout {
  min-height: calc(100vh - 64px);
}

.content {
  background: #f0f2f5;
  padding: 24px;
}
</style>
