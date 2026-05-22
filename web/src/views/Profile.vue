<template>
  <div class="profile">
    <a-card title="个人中心">
      <a-form
        :model="profileForm"
        layout="vertical"
        @finish="handleUpdate"
      >
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="用户名">
              <a-input
                v-model:value="profileForm.username"
                disabled
                placeholder="用户名不可修改"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="昵称">
              <a-input
                v-model:value="profileForm.nickname"
                placeholder="请输入昵称"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="邮箱">
              <a-input
                v-model:value="profileForm.email"
                placeholder="请输入邮箱"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="手机号">
              <a-input
                v-model:value="profileForm.phone"
                placeholder="请输入手机号"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-form-item>
          <a-button type="primary" html-type="submit" :loading="loading">
            保存修改
          </a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </div>
</template>

<script setup lang="ts">
/**
 * 个人中心页面
 * 功能：用户查看和修改自己的信息
 * 对接后端：UserController.update()
 */
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/store/user'
import { userApi } from '@/api'
import { message } from 'ant-design-vue'

const userStore = useUserStore()
const loading = ref(false)
const profileForm = ref({
  id: 0,
  username: '',
  nickname: '',
  email: '',
  phone: ''
})

// 页面加载
onMounted(() => {
  // 从 store 中获取用户信息
  if (userStore.userInfo) {
    profileForm.value = {
      id: userStore.userInfo.id,
      username: userStore.userInfo.username,
      nickname: userStore.userInfo.nickname || '',
      email: userStore.userInfo.email || '',
      phone: userStore.userInfo.phone || ''
    }
  }
})

// 更新用户信息
async function handleUpdate() {
  loading.value = true
  try {
    await userApi.update(profileForm.value)
    message.success('更新成功')
    // 更新 store 中的用户信息
    await userStore.getUserInfo()
  } catch (error) {
    console.error(error)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.profile {
  padding: 0;
  max-width: 800px;
}
</style>
