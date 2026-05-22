/**
 * 路由配置
 * 功能：页面跳转管理，路由守卫（未登录拦截）
 * 技术栈：Vue Router 4
 */
import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import { getToken } from '@/utils/auth'

// 路由配置
const routes: RouteRecordRaw[] = [
  // 登录页
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue')
  },
  // 注册页
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/Register.vue')
  },
  // 主布局（登录后进入）
  {
    path: '/',
    component: () => import('@/views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      // 控制台（首页）
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue')
      },
      // 用户管理（管理员）
      {
        path: 'users',
        name: 'UserList',
        component: () => import('@/views/UserList.vue')
      },
      // 个人中心
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/views/Profile.vue')
      }
    ]
  }
]

// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes
})

/**
 * 路由守卫（类似 Spring 的 Interceptor）
 * 功能：未登录时跳转到登录页
 */
router.beforeEach((to, from, next) => {
  const token = getToken()
  // 如果有 Token 或者访问登录/注册页，放行
  if (token || to.path === '/login' || to.path === '/register') {
    next()
  } else {
    // 否则跳转到登录页
    next('/login')
  }
})

export default router
