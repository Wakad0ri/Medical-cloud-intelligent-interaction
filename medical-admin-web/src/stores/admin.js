import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login, logout } from '@/api/user'

export const useAdminStore = defineStore('admin', () => {
  const token = ref(localStorage.getItem('adminToken') || '')
  const adminInfo = ref(JSON.parse(localStorage.getItem('adminInfo') || '{}'))

  // 管理员登录
  const adminLogin = async (loginForm) => {
    try {
      const response = await login(loginForm)
      if (response.code === 1) {
        token.value = response.data.token
        adminInfo.value = response.data
        
        // 存储到本地
        localStorage.setItem('adminToken', token.value)
        localStorage.setItem('adminInfo', JSON.stringify(adminInfo.value))
        
        return Promise.resolve(response)
      } else {
        return Promise.reject(response)
      }
    } catch (error) {
      return Promise.reject(error)
    }
  }

  // 管理员退出登录
  const adminLogout = async () => {
    try {
      // 调用后端登出接口
      await logout()
      
      // 清除本地存储的管理员信息
      token.value = ''
      adminInfo.value = {}
      localStorage.removeItem('adminToken')
      localStorage.removeItem('adminInfo')
    } catch (error) {
      console.error('管理员退出登录失败:', error)
      // 即使后端接口调用失败，也要清除本地存储
      token.value = ''
      adminInfo.value = {}
      localStorage.removeItem('adminToken')
      localStorage.removeItem('adminInfo')
    }
  }

  return {
    token,
    adminInfo,
    adminLogin,
    adminLogout
  }
})
