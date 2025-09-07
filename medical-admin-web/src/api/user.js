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

// 启用/禁用管理员账号（单个）
export const updateAdminStatus = (id, status) => {
  console.log('API调用参数:', { id, status })
  return request({
    url: `/admin/status/${status}`,
    method: 'put',
    params: {
      ids: [id]
    }
  })
}

// 批量启用/禁用管理员账号
export const batchUpdateAdminStatus = (ids, status) => {
  const queryString = ids.map(id => `ids=${id}`).join('&')
  return request({
    url: `/admin/status/${status}?${queryString}`,
    method: 'put'
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
    params: {
      password,
      newPassword
    }
  })
}

// 删除管理员（支持单个和批量）
export const deleteAdmin = (ids) => {
  const idArray = Array.isArray(ids) ? ids : [ids]
  // 构建查询字符串：ids=1&ids=2&ids=3
  const queryString = idArray.map(id => `ids=${id}`).join('&')
  return request({
    url: `/admin/delete?${queryString}`,
    method: 'delete'
  })
}
