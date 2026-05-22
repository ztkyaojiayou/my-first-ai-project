/**
 * Vue 应用入口文件
 * 功能：初始化 Vue 实例，引入全局依赖库
 * 技术栈：Vue 3 + Ant Design Vue + Pinia + Vue Router + Tailwind CSS
 */

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css' // Ant Design 样式
import './style.css' // Tailwind 自定义样式
import App from './App.vue'
import router from './router'

// 创建 Vue 应用实例
const app = createApp(App)

// 注册 Pinia 状态管理（类似 Spring 的 Bean 容器，集中管理应用状态）
app.use(createPinia())

// 注册路由管理（页面跳转控制）
app.use(router)

// 注册 Ant Design Vue 组件库
app.use(Antd)

// 挂载到页面
app.mount('#app')
