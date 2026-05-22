/**
 * 认证工具类
 * 功能：管理 JWT Token 的存储、获取、删除
 * 对应后端：SecurityConfig 中的 JwtAuthenticationFilter 处理
 */

// Token 在 localStorage 中的 key
const TOKEN_KEY = 'user_token'

/**
 * 存储 Token（登录成功后调用）
 * 对应后端：/api/user/login 返回的 token
 */
export function setToken(token: string) {
  localStorage.setItem(TOKEN_KEY, token)
}

/**
 * 获取 Token（请求时自动添加到请求头）
 */
export function getToken() {
  return localStorage.getItem(TOKEN_KEY)
}

/**
 * 删除 Token（登出时调用）
 */
export function removeToken() {
  localStorage.removeItem(TOKEN_KEY)
}
