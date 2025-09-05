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

// 启用/禁用用户
export const toggleUserStatus = (id, status) => {
  return request({
    url: `/admin/user/status/${id}`,
    method: 'put',
    data: { status }
  })
}
