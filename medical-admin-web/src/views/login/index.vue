<template>
  <div class="admin-login-container">
    <div class="background-pattern"></div>
    <div class="login-card">
      <div class="login-header">
        <div class="admin-icon">
          <el-icon size="48"><Setting /></el-icon>
        </div>
        <h2>管理员控制台</h2>
        <p>医云智能互动系统</p>
      </div>

      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginRules"
        class="login-form"
        @keyup.enter="handleLogin"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="管理员账号"
            size="large"
            prefix-icon="UserFilled"
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="管理员密码"
            size="large"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            size="large"
            style="width: 100%"
            :loading="loading"
            @click="handleLogin"
            class="admin-login-btn"
          >
            {{ loading ? '验证中...' : '进入管理后台' }}
          </el-button>
        </el-form-item>
      </el-form>

      <div class="login-footer">
        <p>默认账号：admin / 123456</p>
        <div class="switch-links">
          <router-link to="/doctor/login" class="switch-link">医生登录</router-link>
          <span class="divider">|</span>
          <router-link to="/user/login" class="switch-link">患者登录</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useAdminStore } from '@/stores/admin'
import { ElMessage } from 'element-plus'
import { Setting } from '@element-plus/icons-vue'

const router = useRouter()
const adminStore = useAdminStore()

const loginFormRef = ref()
const loading = ref(false)

// 登录表单数据
const loginForm = reactive({
  username: 'admin',
  password: '123456'
})

// 表单验证规则
const loginRules = {
  username: [
    { required: true, message: '请输入管理员账号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入管理员密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ]
}

// 处理登录
const handleLogin = async () => {
  if (!loginFormRef.value) return

  try {
    await loginFormRef.value.validate()
    loading.value = true

    await adminStore.adminLogin(loginForm)
    ElMessage.success('管理员登录成功')
    router.push('/admin')
  } catch (error) {
    console.error('管理员登录失败:', error)
    ElMessage.error(error.message || '登录失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.admin-login-container {
  height: 100vh;
  background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  overflow: hidden;
}

.background-pattern {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-image:
    radial-gradient(circle at 20% 80%, rgba(255, 255, 255, 0.1) 0%, transparent 50%),
    radial-gradient(circle at 80% 20%, rgba(255, 255, 255, 0.1) 0%, transparent 50%);
}

.login-card {
  width: 420px;
  padding: 50px 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  box-shadow: 0 25px 50px rgba(0, 0, 0, 0.2);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.3);
  position: relative;
  z-index: 1;
}

.login-header {
  text-align: center;
  margin-bottom: 40px;
}

.admin-icon {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 20px;
  color: white;
  box-shadow: 0 10px 30px rgba(102, 126, 234, 0.3);
}

.login-header h2 {
  color: #2c3e50;
  margin-bottom: 8px;
  font-size: 28px;
  font-weight: 700;
}

.login-header p {
  color: #7f8c8d;
  margin: 0;
  font-size: 16px;
  font-weight: 500;
}

.login-form {
  margin-bottom: 30px;
}

.admin-login-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  font-weight: 600;
  letter-spacing: 1px;
}

.admin-login-btn:hover {
  background: linear-gradient(135deg, #5a6fd8 0%, #6a4190 100%);
}

.login-footer {
  text-align: center;
  color: #95a5a6;
  font-size: 13px;
}

.login-footer p {
  margin: 0 0 15px 0;
}

.switch-links {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.switch-link {
  color: #3498db;
  text-decoration: none;
  font-size: 14px;
  font-weight: 500;
  transition: color 0.3s;
}

.switch-link:hover {
  color: #2980b9;
  text-decoration: underline;
}

.divider {
  color: #bdc3c7;
}
</style>
