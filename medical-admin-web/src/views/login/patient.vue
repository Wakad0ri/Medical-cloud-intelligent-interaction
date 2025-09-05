<template>
  <div class="patient-login-container">
    <div class="floating-shapes">
      <div class="shape shape-1"></div>
      <div class="shape shape-2"></div>
      <div class="shape shape-3"></div>
      <div class="shape shape-4"></div>
    </div>

    <div class="login-content">
      <div class="welcome-section">
        <div class="health-icon">
          <el-icon size="60"><FirstAidKit /></el-icon>
        </div>
        <h1>欢迎使用医云平台</h1>
        <p>您的健康，我们的关怀</p>

        <div class="service-cards">
          <div class="service-card">
            <el-icon><Calendar /></el-icon>
            <h3>在线预约</h3>
            <p>快速预约专家号</p>
          </div>
          <div class="service-card">
            <el-icon><ChatDotRound /></el-icon>
            <h3>在线问诊</h3>
            <p>24小时医生咨询</p>
          </div>
          <div class="service-card">
            <el-icon><Document /></el-icon>
            <h3>健康档案</h3>
            <p>个人健康管理</p>
          </div>
        </div>
      </div>

      <div class="login-section">
        <div class="login-card">
          <div class="card-header">
            <div class="user-icon">
              <el-icon size="32"><User /></el-icon>
            </div>
            <h2>患者登录</h2>
            <p>开启您的健康之旅</p>
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
                placeholder="手机号/身份证号"
                size="large"
                prefix-icon="Phone"
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
                class="patient-login-btn"
              >
                {{ loading ? '登录中...' : '立即登录' }}
              </el-button>
            </el-form-item>
          </el-form>

          <div class="quick-actions">
            <a href="#" class="quick-link">忘记密码？</a>
            <a href="#" class="quick-link register">立即注册</a>
          </div>

          <div class="login-footer">
            <p>测试账号：patient / 123456</p>
            <div class="switch-links">
              <router-link to="/admin/login" class="switch-link">管理员登录</router-link>
              <span class="divider">|</span>
              <router-link to="/doctor/login" class="switch-link">医生登录</router-link>
            </div>
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
  FirstAidKit,
  User,
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
  username: 'patient',
  password: '123456',
  userType: 1 // 固定为患者
})

// 表单验证规则
const loginRules = {
  username: [
    { required: true, message: '请输入手机号或身份证号', trigger: 'blur' }
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

    // 患者登录功能暂未实现
    ElMessage.warning('患者登录功能开发中，请稍后...')
  } catch (error) {
    console.error('登录失败:', error)
    ElMessage.error('请填写完整的登录信息')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.patient-login-container {
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  position: relative;
  overflow: hidden;
}

.floating-shapes {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  pointer-events: none;
}

.shape {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  animation: float 6s ease-in-out infinite;
}

.shape-1 {
  width: 80px;
  height: 80px;
  top: 20%;
  left: 10%;
  animation-delay: 0s;
}

.shape-2 {
  width: 120px;
  height: 120px;
  top: 60%;
  right: 15%;
  animation-delay: 2s;
}

.shape-3 {
  width: 60px;
  height: 60px;
  bottom: 20%;
  left: 20%;
  animation-delay: 4s;
}

.shape-4 {
  width: 100px;
  height: 100px;
  top: 10%;
  right: 30%;
  animation-delay: 1s;
}

@keyframes float {
  0%, 100% { transform: translateY(0px) rotate(0deg); }
  50% { transform: translateY(-20px) rotate(180deg); }
}

.login-content {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 80px;
  position: relative;
  z-index: 1;
}

.welcome-section {
  flex: 1;
  color: white;
  padding-right: 60px;
}

.health-icon {
  margin-bottom: 30px;
}

.welcome-section h1 {
  font-size: 48px;
  font-weight: 700;
  margin-bottom: 15px;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}

.welcome-section > p {
  font-size: 20px;
  margin-bottom: 50px;
  opacity: 0.9;
}

.service-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
}

.service-card {
  background: rgba(255, 255, 255, 0.1);
  padding: 25px;
  border-radius: 15px;
  text-align: center;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.service-card .el-icon {
  font-size: 32px;
  margin-bottom: 15px;
  color: #fff;
}

.service-card h3 {
  font-size: 18px;
  margin-bottom: 8px;
  font-weight: 600;
}

.service-card p {
  font-size: 14px;
  opacity: 0.8;
  margin: 0;
}

.login-section {
  width: 400px;
}

.login-card {
  background: rgba(255, 255, 255, 0.95);
  padding: 40px;
  border-radius: 20px;
  box-shadow: 0 25px 50px rgba(0, 0, 0, 0.2);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.3);
}

.card-header {
  text-align: center;
  margin-bottom: 30px;
}

.user-icon {
  width: 64px;
  height: 64px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 20px;
  color: white;
  box-shadow: 0 10px 30px rgba(102, 126, 234, 0.3);
}

.card-header h2 {
  color: #2c3e50;
  margin-bottom: 8px;
  font-size: 24px;
  font-weight: 700;
}

.card-header p {
  color: #7f8c8d;
  margin: 0;
  font-size: 14px;
}

.login-form {
  margin-bottom: 20px;
}

.patient-login-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  font-weight: 600;
  letter-spacing: 1px;
}

.patient-login-btn:hover {
  background: linear-gradient(135deg, #5a6fd8 0%, #6a4190 100%);
}

.quick-actions {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.quick-link {
  color: #7f8c8d;
  text-decoration: none;
  font-size: 14px;
  transition: color 0.3s;
}

.quick-link:hover {
  color: #667eea;
}

.quick-link.register {
  color: #667eea;
  font-weight: 600;
}

.login-footer {
  text-align: center;
  color: #95a5a6;
  font-size: 12px;
}

.login-footer p {
  margin: 0 0 10px 0;
}

.switch-links {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.switch-link {
  color: #667eea;
  text-decoration: none;
  font-size: 13px;
  transition: color 0.3s;
}

.switch-link:hover {
  color: #5a6fd8;
  text-decoration: underline;
}

.divider {
  color: #bdc3c7;
}
</style>
