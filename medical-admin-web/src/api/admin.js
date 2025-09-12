import request from '@/utils/request'

/**
 * 员工管理API (AdminController)
 * 路径前缀: /admin
 *
 * 根据后端Controller重新实现
 */

// 管理员登录
export const login = (data) => {
  return request({
    url: '/admin/login',
    method: 'post',
    data
  })
}

// 管理员登出
export const logout = () => {
  return request({
    url: '/admin/logout',
    method: 'post'
  })
}

// 添加管理员
export const addAdmin = (data) => {
  return request({
    url: '/admin/add',
    method: 'post',
    data
  })
}

// 分页查询管理员
export const getAdminPage = (params) => {
  return request({
    url: '/admin/page',
    method: 'get',
    params
  })
}

// 启用禁用管理员
export const updateAdminStatus = (status, ids) => {
  return request({
    url: `/admin/status/${status}`,
    method: 'put',
    params: { ids: ids.join(',') }
  })
}

// 修改管理员信息
export const updateAdmin = (data) => {
  return request({
    url: '/admin/update',
    method: 'put',
    data
  })
}

// 修改管理员密码
export const updateAdminPassword = (password, newPassword) => {
  return request({
    url: '/admin/password',
    method: 'put',
    params: { password, newPassword }
  })
}

// 删除管理员
export const deleteAdmin = (ids) => {
  return request({
    url: '/admin/delete',
    method: 'delete',
    params: { ids: ids.join(',') }
  })
}

// 批量启用禁用管理员
export const batchUpdateAdminStatus = (status, ids) => {
  return request({
    url: `/admin/status/${status}`,
    method: 'put',
    params: { ids: ids.join(',') }
  })
}

// 获取管理员选项
export const getAdminOptions = () => {
  return request({
    url: '/admin/admin-options',
    method: 'get'
  })
}
