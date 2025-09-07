import request from '@/utils/request'

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

// 删除文件
export const deleteFile = (fileUrl) => {
  return request({
    url: '/common/delete',
    method: 'delete',
    params: { fileUrl }
  })
}
