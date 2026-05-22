/**
 * API 接口统一封装
 * 功能：所有与后端对接的请求都在这里定义
 * 对接后端：UserController、RoleController 等
 */
import request from '@/utils/request'

/**
 * 用户相关接口
 */
export const userApi = {
  /**
   * 用户登录
   * POST /api/user/login
   * 后端：UserController.login()
   */
  login(data: { username: string; password: string }) {
    return request.post('/user/login', data)
  },

  /**
   * 用户注册
   * POST /api/user/register
   * 后端：UserController.register()
   */
  register(data: { username: string; password: string; nickname?: string; email?: string; phone?: string }) {
    return request.post('/user/register', data)
  },

  /**
   * 获取当前登录用户信息
   * GET /api/user/info
   * 后端：UserController.getUserInfo()
   */
  getInfo() {
    return request.get('/user/info')
  },

  /**
   * 用户登出
   * POST /api/user/logout
   * 后端：UserController.logout()
   */
  logout() {
    return request.post('/user/logout')
  },

  /**
   * 获取用户列表（管理员权限）
   * GET /api/user/list
   * 后端：UserController.listUsers()
   */
  getList(params: { pageNum?: number; pageSize?: number }) {
    return request.get('/user/list', { params })
  },

  /**
   * 更新用户信息
   * PUT /api/user
   * 后端：UserController.updateUser()
   */
  update(data: any) {
    return request.put('/user', data)
  },

  /**
   * 删除用户（管理员权限）
   * DELETE /api/user/{id}
   * 后端：UserController.deleteUser()
   */
  delete(id: number) {
    return request.delete(`/user/${id}`)
  }
}
