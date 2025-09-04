import request from '@/utils/request'

// 用户登录
export const login = (data) => {
  return request({
    url: '/admin/user/login',
    method: 'post',
    data
  })
}

// 用户退出
export const logout = () => {
  return request({
    url: '/admin/user/logout',
    method: 'post'
  })
}

// 获取用户列表
export const getUserList = (params) => {
  return request({
    url: '/admin/user/list',
    method: 'get',
    params
  })
}

// 添加用户
export const addUser = (data) => {
  return request({
    url: '/admin/user/add',
    method: 'post',
    data
  })
}

// 更新用户
export const updateUser = (data) => {
  return request({
    url: '/admin/user/update',
    method: 'put',
    data
  })
}

// 删除用户
export const deleteUser = (id) => {
  return request({
    url: `/admin/user/delete/${id}`,
    method: 'delete'
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
