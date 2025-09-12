<template>
  <div class="admin-layout">
    <el-container>
      <!-- 头部 -->
      <el-header class="header">
        <div class="header-left">
          <h2>医云智能互动系统 - 管理员控制台</h2>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-icon><User /></el-icon>
              {{ adminStore.adminInfo.username || 'admin' }}
              <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="changePassword">修改密码</el-dropdown-item>
                <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-container>
        <!-- 侧边栏 -->
        <el-aside width="200px" class="sidebar">
          <el-menu
            :default-active="activeMenu"
            class="sidebar-menu"
            @select="handleMenuSelect"
          >
            <el-menu-item index="/admin">
              <el-icon><House /></el-icon>
              <span>首页</span>
            </el-menu-item>
            <el-menu-item index="/admin/users">
              <el-icon><User /></el-icon>
              <span>用户管理</span>
            </el-menu-item>
            <el-menu-item index="/admin/doctors">
              <el-icon><Avatar /></el-icon>
              <span>医生管理</span>
            </el-menu-item>
            <el-menu-item index="/admin/page">
              <el-icon><UserFilled /></el-icon>
              <span>员工管理</span>
            </el-menu-item>
            <el-menu-item index="/admin/department">
              <el-icon><OfficeBuilding /></el-icon>
              <span>科室管理</span>
            </el-menu-item>
            <el-menu-item index="/admin/appointments">
              <el-icon><Calendar /></el-icon>
              <span>预约管理</span>
            </el-menu-item>
            <el-menu-item index="/admin/statistics">
              <el-icon><DataAnalysis /></el-icon>
              <span>数据统计</span>
            </el-menu-item>
          </el-menu>
        </el-aside>

        <!-- 主内容区 -->
        <el-main class="main-content">
          <router-view />
        </el-main>
      </el-container>
    </el-container>

    <!-- 修改密码对话框 -->
    <el-dialog
      v-model="passwordDialogVisible"
      title="修改密码"
      width="400px"
      :close-on-click-modal="false"
    >
      <el-form
        ref="passwordFormRef"
        :model="passwordForm"
        :rules="passwordRules"
        label-width="100px"
      >
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input
            v-model="passwordForm.oldPassword"
            type="password"
            placeholder="请输入当前密码"
            show-password
          />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input
            v-model="passwordForm.newPassword"
            type="password"
            placeholder="请输入新密码"
            show-password
          />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            v-model="passwordForm.confirmPassword"
            type="password"
            placeholder="请再次输入新密码"
            show-password
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="passwordDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handlePasswordSubmit" :loading="passwordLoading">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAdminStore } from '@/stores/admin'
import { ElMessage, ElMessageBox } from 'element-plus'
import { updateAdminPassword } from '@/api/admin'
import {
  User,
  ArrowDown,
  House,
  Avatar,
  UserFilled,
  OfficeBuilding,
  Calendar,
  DataAnalysis
} from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const adminStore = useAdminStore()

// 密码修改相关
const passwordDialogVisible = ref(false)
const passwordFormRef = ref()
const passwordLoading = ref(false)

// 密码表单数据
const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 密码验证规则
const passwordRules = {
  oldPassword: [
    { required: true, message: '请输入当前密码', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入新密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== passwordForm.newPassword) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

const activeMenu = computed(() => route.path)

// 处理菜单选择
const handleMenuSelect = (index) => {
  router.push(index)
}

// 处理用户下拉菜单
const handleCommand = async (command) => {
  if (command === 'logout') {
    try {
      await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })

      await adminStore.adminLogout()
      ElMessage.success('管理员退出成功')
      router.push('/login')
    } catch (error) {
      if (error !== 'cancel') {
        ElMessage.error('退出失败')
      }
    }
  } else if (command === 'changePassword') {
    // 打开修改密码对话框
    passwordDialogVisible.value = true
    // 重置表单
    passwordForm.oldPassword = ''
    passwordForm.newPassword = ''
    passwordForm.confirmPassword = ''
  }
}

// 提交密码修改
const handlePasswordSubmit = async () => {
  try {
    // 表单验证
    await passwordFormRef.value.validate()

    passwordLoading.value = true

    // 调用修改密码接口
    const response = await updateAdminPassword(passwordForm.oldPassword, passwordForm.newPassword)

    if (response.code === 1) {
      ElMessage.success('修改密码成功')
      passwordDialogVisible.value = false
      // 重置表单
      passwordForm.oldPassword = ''
      passwordForm.newPassword = ''
      passwordForm.confirmPassword = ''
    } else {
      ElMessage.error(response.msg || '修改密码失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('修改密码失败:', error)
      // 检查是否是后端返回的业务异常
      if (error.msg) {
        // 后端业务异常，直接显示错误信息
        ElMessage.error(error.msg)
      } else if (error.response && error.response.data && error.response.data.msg) {
        // HTTP错误响应
        ElMessage.error(error.response.data.msg)
      } else {
        ElMessage.error('修改密码失败')
      }
    }
  } finally {
    passwordLoading.value = false
  }
}
</script>

<style scoped>
.admin-layout {
  height: 100vh;
}

.header {
  background: #fff;
  border-bottom: 1px solid #e6e6e6;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.header-left h2 {
  margin: 0;
  color: #333;
  font-size: 18px;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
  color: #333;
}

.user-info .el-icon {
  margin: 0 5px;
}

.sidebar {
  background: #f5f5f5;
  border-right: 1px solid #e6e6e6;
}

.sidebar-menu {
  border: none;
  background: transparent;
}

.main-content {
  background: #f0f2f5;
  padding: 20px;
}
</style>
