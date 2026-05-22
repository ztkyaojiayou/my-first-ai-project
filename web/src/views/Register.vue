<template>
  <div class="register-container">
    <div class="background"></div>

    <div class="register-card">
      <div class="card-content">
        <div class="header">
          <h1>用户管理系统</h1>
          <p>创建新账号</p>
        </div>

        <a-form
          :model="registerForm"
          layout="vertical"
          @finish="handleRegister"
        >
          <a-form-item
            label="用户名"
            name="username"
            :rules="[{ required: true, message: '请输入用户名' }]"
          >
            <a-input
              v-model:value="registerForm.username"
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
              v-model:value="registerForm.password"
              placeholder="请输入密码"
              size="large"
            />
          </a-form-item>

          <a-form-item
            label="昵称"
            name="nickname"
          >
            <a-input
              v-model:value="registerForm.nickname"
              placeholder="请输入昵称（可选）"
              size="large"
            />
          </a-form-item>

          <a-form-item
            label="邮箱"
            name="email"
          >
            <a-input
              v-model:value="registerForm.email"
              placeholder="请输入邮箱（可选）"
              size="large"
            />
          </a-form-item>

          <a-form-item
            label="手机号"
            name="phone"
          >
            <a-input
              v-model:value="registerForm.phone"
              placeholder="请输入手机号（可选）"
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
              注册
            </a-button>
          </a-form-item>

          <div class="footer">
            <span>已有账号？</span>
            <a @click="$router.push('/login')">立即登录</a>
          </div>
        </a-form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
/**
 * 注册页面
 * 功能：用户注册，调用后端 /api/user/register
 * 对接后端：UserController.register()
 */
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { userApi } from '@/api'
import { message } from 'ant-design-vue'

const router = useRouter()

const loading = ref(false)
const registerForm = ref({
  username: '',
  password: '',
  nickname: '',
  email: '',
  phone: ''
})

// 注册
async function handleRegister() {
  loading.value = true
  try {
    // 调用后端注册接口
    await userApi.register(registerForm.value)
    message.success('注册成功，请登录')
    // 注册成功后跳转到登录页
    router.push('/login')
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
  padding: 40px 0;
}

.background {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.register-card {
  position: relative;
  z-index: 1;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  width: 450px;
  padding: 40px;
}

.header {
  text-align: center;
  margin-bottom: 24px;
}

.header h1 {
  color: #333;
  font-size: 24px;
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
