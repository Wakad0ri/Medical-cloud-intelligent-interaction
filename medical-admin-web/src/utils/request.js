import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useAdminStore } from '@/stores/admin'
import router from '@/router'

// 防重复提示标志
let isLogoutProcessing = false

// 创建axios实例
const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    const adminStore = useAdminStore()
    if (adminStore.token) {
      config.headers['token'] = adminStore.token
    }
    return config
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    const res = response.data

    // 如果返回的状态码为1，说明接口请求成功，可以正常拿到数据
    if (res.code === 1) {
      return res
    } else {
      // 其他状态码都当作错误处理，但不在这里显示错误信息
      // 让具体的业务代码来处理错误显示
      return Promise.reject(res)
    }
  },
  error => {
    console.error('响应错误:', error)

    if (error.response) {
      const { status } = error.response

      switch (status) {
        case 401:
          // 防止重复提示和重复跳转
          if (!isLogoutProcessing) {
            isLogoutProcessing = true
            ElMessage.error('登录已过期，请重新登录')
            const adminStore = useAdminStore()
            adminStore.adminLogout()
            router.push('/login')
            // 2秒后重置标志，防止用户重新登录后无法正常提示
            setTimeout(() => {
              isLogoutProcessing = false
            }, 2000)
          }
          break
        case 403:
          ElMessage.error('没有权限访问')
          break
        case 404:
          ElMessage.error('请求的资源不存在')
          break
        case 500:
          ElMessage.error('服务器内部错误')
          break
        default:
          ElMessage.error('网络错误，请稍后重试')
      }
    } else {
      ElMessage.error('网络错误，请检查网络连接')
    }

    return Promise.reject(error)
  }
)

export default request
