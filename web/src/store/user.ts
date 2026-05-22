/**
 * 用户状态管理
 * 功能：集中管理用户登录状态、用户信息（类似 Spring 的 Session）
 * 技术栈：Pinia
 */
import { defineStore } from 'pinia'
import { ref } from 'vue'
import { userApi } from '@/api'
import { setToken, removeToken } from '@/utils/auth'

export const useUserStore = defineStore('user', () => {
  // 状态定义（类似 Java 的成员变量）
  const userInfo = ref<any>(null) // 用户信息
  const token = ref<string>('') // JWT Token

  /**
   * 登录
   * 调用后端：/api/user/login
   */
  async function login(loginForm: { username: string; password: string }) {
    const res = await userApi.login(loginForm)
    // 后端返回 { token: "xxx" }
    token.value = res.token
    setToken(res.token)
    // 登录成功后，获取用户信息
    await getUserInfo()
  }

  /**
   * 获取用户信息
   * 调用后端：/api/user/info
   */
  async function getUserInfo() {
    const res = await userApi.getInfo()
    userInfo.value = res
  }

  /**
   * 登出
   * 调用后端：/api/user/logout
   */
  async function logout() {
    await userApi.logout()
    // 清空本地状态
    userInfo.value = null
    token.value = ''
    removeToken()
  }

  // 返回给组件使用
  return {
    userInfo,
    token,
    login,
    getUserInfo,
    logout
  }
})
