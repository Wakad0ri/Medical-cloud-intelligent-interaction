import request from '@/utils/request'

/**
 * 通用API (CommonController)
 * 路径前缀: /admin/common
 */

// 文件上传
export const uploadFile = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  
  return request({
    url: '/admin/common/upload',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
