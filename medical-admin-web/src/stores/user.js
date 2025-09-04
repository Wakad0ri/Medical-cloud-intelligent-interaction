import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login, logout } from '@/api/user'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || '{}'))

  // 登录
  const userLogin = async (loginForm) => {
    try {
      const response = await login(loginForm)
      if (response.code === 200) {
        token.value = response.data.token
        userInfo.value = response.data
        
        // 存储到本地
        localStorage.setItem('token', token.value)
        localStorage.setItem('userInfo', JSON.stringify(userInfo.value))
        
        return Promise.resolve(response)
      } else {
        return Promise.reject(response)
      }
    } catch (error) {
      return Promise.reject(error)
    }
  }

  // 退出登录
  const userLogout = async () => {
    try {
      await logout()
      token.value = ''
      userInfo.value = {}
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
    } catch (error) {
      console.error('退出登录失败:', error)
    }
  }

  return {
    token,
    userInfo,
    userLogin,
    userLogout
  }
})
