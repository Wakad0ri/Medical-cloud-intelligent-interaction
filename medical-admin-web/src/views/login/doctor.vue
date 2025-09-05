<template>
  <div class="doctor-login-container">
    <div class="left-panel">
      <div class="medical-illustration">
        <div class="stethoscope-icon">
          <el-icon size="120"><Stethoscope /></el-icon>
        </div>
        <h1>医生工作台</h1>
        <p>专业医疗服务平台</p>
        <div class="features">
          <div class="feature-item">
            <el-icon><Calendar /></el-icon>
            <span>智能排班管理</span>
          </div>
          <div class="feature-item">
            <el-icon><ChatDotRound /></el-icon>
            <span>在线问诊服务</span>
          </div>
          <div class="feature-item">
            <el-icon><Document /></el-icon>
            <span>患者档案管理</span>
          </div>
        </div>
      </div>
    </div>

    <div class="right-panel">
      <div class="login-form-container">
        <div class="form-header">
          <div class="doctor-avatar">
            <el-icon size="40"><Avatar /></el-icon>
          </div>
          <h2>医生登录</h2>
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
              placeholder="医生工号/手机号"
              size="large"
              prefix-icon="UserFilled"
            />
          </el-form-item>

          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="登录密码"
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
              class="doctor-login-btn"
            >
              {{ loading ? '登录中...' : '进入工作台' }}
            </el-button>
          </el-form-item>
        </el-form>

        <div class="login-footer">
          <p>测试账号：doctor / 123456</p>
          <div class="switch-links">
            <router-link to="/admin/login" class="switch-link">管理员登录</router-link>
            <span class="divider">|</span>
            <router-link to="/user/login" class="switch-link">患者登录</router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'
import {
  Stethoscope,
  Avatar,
  Calendar,
  ChatDotRound,
  Document
} from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()

const loginFormRef = ref()
const loading = ref(false)

// 登录表单数据
const loginForm = reactive({
  username: 'doctor',
  password: '123456',
  userType: 2 // 固定为医生
})

// 表单验证规则
const loginRules = {
  username: [
    { required: true, message: '请输入医生工号或手机号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入登录密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ]
}

// 处理登录
const handleLogin = async () => {
  if (!loginFormRef.value) return

  try {
    await loginFormRef.value.validate()
    loading.value = true

    // 医生登录功能暂未实现
    ElMessage.warning('医生登录功能开发中，请稍后...')
  } catch (error) {
    console.error('登录失败:', error)
    ElMessage.error('请填写完整的登录信息')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.doctor-login-container {
  height: 100vh;
  display: flex;
}

.left-panel {
  flex: 1;
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  position: relative;
  overflow: hidden;
}

.left-panel::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><defs><pattern id="grid" width="10" height="10" patternUnits="userSpaceOnUse"><path d="M 10 0 L 0 0 0 10" fill="none" stroke="rgba(255,255,255,0.1)" stroke-width="0.5"/></pattern></defs><rect width="100" height="100" fill="url(%23grid)"/></svg>');
}

.medical-illustration {
  text-align: center;
  z-index: 1;
  position: relative;
}

.stethoscope-icon {
  margin-bottom: 30px;
  opacity: 0.9;
}

.medical-illustration h1 {
  font-size: 48px;
  font-weight: 700;
  margin-bottom: 15px;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.medical-illustration p {
  font-size: 20px;
  margin-bottom: 40px;
  opacity: 0.9;
}

.features {
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: flex-start;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 15px;
  font-size: 18px;
  font-weight: 500;
}

.feature-item .el-icon {
  font-size: 24px;
}

.right-panel {
  width: 480px;
  background: white;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: -10px 0 30px rgba(0, 0, 0, 0.1);
}

.login-form-container {
  width: 350px;
  padding: 40px 0;
}

.form-header {
  text-align: center;
  margin-bottom: 40px;
}

.doctor-avatar {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 20px;
  color: white;
  box-shadow: 0 10px 30px rgba(79, 172, 254, 0.3);
}

.form-header h2 {
  color: #2c3e50;
  margin-bottom: 8px;
  font-size: 28px;
  font-weight: 700;
}

.form-header p {
  color: #7f8c8d;
  margin: 0;
  font-size: 16px;
}

.login-form {
  margin-bottom: 30px;
}

.doctor-login-btn {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  border: none;
  font-weight: 600;
  letter-spacing: 1px;
}

.doctor-login-btn:hover {
  background: linear-gradient(135deg, #3d8bfe 0%, #00d4fe 100%);
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
  color: #4facfe;
  text-decoration: none;
  font-size: 14px;
  font-weight: 500;
  transition: color 0.3s;
}

.switch-link:hover {
  color: #3d8bfe;
  text-decoration: underline;
}

.divider {
  color: #bdc3c7;
}
</style>
