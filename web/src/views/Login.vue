<template>
  <div class="login-container">
    <!-- 渐变背景 -->
    <div class="background"></div>

    <!-- 登录卡片 -->
    <div class="login-card">
      <div class="card-content">
        <div class="header">
          <h1>用户管理系统</h1>
          <p>欢迎登录</p>
        </div>

        <a-form
          :model="loginForm"
          layout="vertical"
          @finish="handleLogin"
        >
          <a-form-item
            label="用户名"
            name="username"
            :rules="[{ required: true, message: '请输入用户名' }]"
          >
            <a-input
              v-model:value="loginForm.username"
              placeholder="请输入用户名"
              size="large"
            />
          </a-form-item>

          <a-form-item
            label="密码"
            name="password"
            :rules="[{ required: true, message: '请输入密码' }]"
          >
            <a-input-password
              v-model:value="loginForm.password"
              placeholder="请输入密码"
              size="large"
            />
          </a-form-item>

          <a-form-item>
            <a-button
              type="primary"
              html-type="submit"
              size="large"
              :loading="loading"
              block
            >
              登录
            </a-button>
          </a-form-item>

          <div class="footer">
            <span>还没有账号？</span>
            <a @click="$router.push('/register')">立即注册</a>
          </div>
        </a-form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
/**
 * 登录页面
 * 功能：用户登录，调用后端 /api/user/login
 * 对接后端：UserController.login()
 */
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { message } from 'ant-design-vue'

const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const loginForm = ref({
  username: '',
  password: ''
})

// 登录
async function handleLogin() {
  loading.value = true
  try {
    // 调用后端登录接口
    await userStore.login(loginForm.value)
    message.success('登录成功')
    // 登录成功后跳转到首页
    router.push('/dashboard')
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
}

/* 渐变背景，与后端欢迎页呼应 */
.background {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  position: relative;
  z-index: 1;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  width: 400px;
  padding: 48px 40px;
}

.header {
  text-align: center;
  margin-bottom: 32px;
}

.header h1 {
  color: #333;
  font-size: 28px;
  margin-bottom: 8px;
}

.header p {
  color: #666;
}

.footer {
  text-align: center;
  color: #666;
}

.footer a {
  color: #667eea;
  font-weight: 500;
}
</style>
