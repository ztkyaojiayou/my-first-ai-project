/**
 * Vite 配置文件
 * 功能：构建工具配置，开发服务器代理、路径别名、构建优化
 * 技术栈：Vite 5
 */
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

export default defineConfig({
  // Vue 3 插件
  plugins: [vue()],
  
  // 路径别名，方便导入
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    }
  },
  
  // 开发服务器配置
  server: {
    port: 3000,
    open: true,
    // 代理配置：将 /api 开头的请求转发到后端服务
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // 后端地址
        changeOrigin: true
      }
    }
  }
})
