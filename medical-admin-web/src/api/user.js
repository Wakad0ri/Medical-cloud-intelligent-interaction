import request from '@/utils/request'

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

// 启用/禁用用户
export const toggleUserStatus = (id, status) => {
  return request({
    url: `/admin/user/status/${id}`,
    method: 'put',
    data: { status }
  })
}
