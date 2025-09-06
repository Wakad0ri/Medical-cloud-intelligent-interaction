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
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
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
            <el-menu-item index="/admin/employees">
              <el-icon><UserFilled /></el-icon>
              <span>员工管理</span>
            </el-menu-item>
            <el-menu-item index="/admin/departments">
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
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAdminStore } from '@/stores/admin'
import { ElMessage, ElMessageBox } from 'element-plus'
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
