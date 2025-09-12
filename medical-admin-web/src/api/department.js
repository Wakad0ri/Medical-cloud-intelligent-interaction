import request from '@/utils/request'

/**
 * 科室管理API
 */

// 分页查询科室
export const getDepartmentPage = (params) => {
  return request({
    url: '/admin/department/page',
    method: 'get',
    params
  })
}

// 获取科室选项（下拉框）
export const getDepartmentOptions = () => {
  return request({
    url: '/admin/department/department-options',
    method: 'get'
  })
}

// 根据ID查询科室
export const getDepartmentById = (id) => {
  return request({
    url: `/admin/department/detail/${id}`,
    method: 'get'
  })
}

// 新增科室
export const addDepartment = (data) => {
  return request({
    url: '/admin/department/add',
    method: 'post',
    data
  })
}

// 更新科室信息
export const updateDepartment = (data) => {
  return request({
    url: '/admin/department/update',
    method: 'put',
    data
  })
}

// 批量更新科室状态
export const batchUpdateDepartmentStatus = (status, ids) => {
  return request({
    url: `/admin/department/status/${status}`,
    method: 'put',
    params: { ids: ids.join(',') }
  })
}

// 批量删除科室
export const batchDeleteDepartment = (ids) => {
  return request({
    url: '/admin/department',
    method: 'delete',
    params: { ids: ids.join(',') }
  })
}
