import request from '@/utils/request'

/**
 * 医生管理API
 */

// 分页查询医生
export const getDoctorPage = (params) => {
  return request({
    url: '/admin/doctor/page',
    method: 'get',
    params
  })
}

// 新增医生
export const addDoctor = (data) => {
  return request({
    url: '/admin/doctor',
    method: 'post',
    data
  })
}

// 根据ID查询医生
export const getDoctorById = (id) => {
  return request({
    url: `/admin/doctor/${id}`,
    method: 'get'
  })
}

// 更新医生信息
export const updateDoctor = (data) => {
  return request({
    url: '/admin/doctor',
    method: 'put',
    data
  })
}

// 批量更新医生状态
export const updateDoctorStatus = (status, ids) => {
  return request({
    url: `/admin/doctor/status/${status}`,
    method: 'post',
    data: ids
  })
}

// 批量删除医生
export const deleteDoctors = (ids) => {
  return request({
    url: '/admin/doctor',
    method: 'delete',
    params: { ids: ids.join(',') }
  })
}

// 获取医生选项
export const getDoctorOptions = () => {
  return request({
    url: '/admin/doctor/options',
    method: 'get'
  })
}
