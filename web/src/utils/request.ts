/**
 * Axios 请求封装
 * 功能：统一请求/响应拦截、错误处理、自动携带 Token
 * 对接后端：所有 /api/** 接口
 */
import axios from 'axios'
import { getToken } from './auth'
import { message } from 'ant-design-vue'
import router from '@/router'

// 创建 axios 实例
const request = axios.create({
  baseURL: '/api', // 代理前缀，通过 vite.config.ts 转发到 http://localhost:8080
  timeout: 10000
})

/**
 * 请求拦截器
 * 功能：请求发送前，自动添加 JWT Token 到请求头
 * 对应后端：需要 Authorization: Bearer {token}
 */
request.interceptors.request.use(
  (config) => {
    // 获取本地存储的 Token
    const token = getToken()
    if (token) {
      // 自动添加到请求头
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

/**
 * 响应拦截器
 * 功能：统一处理后端返回的 Result 格式，自动跳转登录
 * 对接后端：Result<T> 格式 { code, msg, data }
 */
request.interceptors.response.use(
  (response) => {
    const res = response.data
    // 根据后端的 Result 结构判断是否成功
    if (res.code === 200) {
      // 成功，返回 data
      return res.data
    } else {
      // 失败，显示错误信息
      message.error(res.msg || '请求失败')
      return Promise.reject(new Error(res.msg))
    }
  },
  (error) => {
    // 处理 HTTP 状态码错误
    if (error.response?.status === 401) {
      // 401 未授权，跳转登录页
      message.error('登录已过期，请重新登录')
      router.push('/login')
    } else {
      message.error(error.message || '网络错误')
    }
    return Promise.reject(error)
  }
)

export default request
