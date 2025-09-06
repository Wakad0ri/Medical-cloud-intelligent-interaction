<template>
  <div class="admin-management">
    <!-- 搜索区域 -->
    <el-card class="search-card">
      <el-form :model="searchForm" :inline="true" class="search-form">
        <el-form-item label="员工姓名">
          <el-input
            v-model="searchForm.realName"
            placeholder="请输入员工姓名"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="用户名">
          <el-input
            v-model="searchForm.username"
            placeholder="请输入用户名"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态" style="width: 120px">
            <el-option label="全部" value="" />
            <el-option label="启用" value="1" />
            <el-option label="禁用" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch" :loading="loading">
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button @click="handleReset">
            <el-icon><Refresh /></el-icon>
            重置
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 操作区域 -->
    <el-card class="operation-card">
      <div class="operation-header">
        <h3>员工列表</h3>
        <el-button type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon>
          新增员工
        </el-button>
      </div>
    </el-card>

    <!-- 表格区域 -->
    <el-card class="table-card">
      <el-table
        :data="adminList"
        v-loading="loading"
        stripe
        style="width: 100%"
        empty-text="暂无数据"
      >
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="realName" label="真实姓名" width="120" />
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="email" label="邮箱" width="180" />
        <el-table-column prop="sex" label="性别" width="80">
          <template #default="{ row }">
            <el-tag :type="row.sex === 1 ? 'primary' : 'success'">
              {{ row.sex === 1 ? '男' : '女' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" width="80" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">
              编辑
            </el-button>
            <el-button
              :type="row.status === 1 ? 'warning' : 'success'"
              size="small"
              @click="handleToggleStatus(row)"
            >
              {{ row.status === 1 ? '禁用' : '启用' }}
            </el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="pagination.page"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, Plus } from '@element-plus/icons-vue'

const router = useRouter()

// 加载状态
const loading = ref(false)

// 搜索表单
const searchForm = reactive({
  realName: '',
  username: '',
  status: ''
})

// 分页信息
const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

// Admin列表（模拟数据）
const adminList = ref([
  {
    id: 1,
    username: 'admin',
    realName: '超级管理员',
    phone: '13888888888',
    email: 'admin@example.com',
    sex: 1,
    age: 30,
    status: 1,
    createTime: '2023-05-01 10:00:00'
  },
  {
    id: 2,
    username: 'admin2',
    realName: '管理员二',
    phone: '13999999999',
    email: 'admin2@example.com',
    sex: 0,
    age: 25,
    status: 1,
    createTime: '2023-05-02 10:00:00'
  }
])

// 搜索
const handleSearch = () => {
  loading.value = true
  // TODO: 调用后端搜索接口
  setTimeout(() => {
    loading.value = false
    ElMessage.info('搜索功能待后端接口完善后实现')
  }, 1000)
}

// 重置搜索
const handleReset = () => {
  Object.assign(searchForm, {
    realName: '',
    username: '',
    status: ''
  })
  handleSearch()
}

// 新增员工
const handleAdd = () => {
  router.push('/admin/add')
}

// 编辑员工
const handleEdit = (row) => {
  ElMessage.info(`编辑员工功能待后端接口完善后实现，员工 ID: ${row.id}`)
}

// 切换状态
const handleToggleStatus = (row) => {
  ElMessage.info(`切换状态功能待后端接口完善后实现，员工 ID: ${row.id}`)
}

// 删除员工
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(`确定要删除员工"${row.realName}"吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    ElMessage.info(`删除员工功能待后端接口完善后实现，员工 ID: ${row.id}`)
  } catch (error) {
    // 用户取消删除
  }
}

// 分页大小改变
const handleSizeChange = (size) => {
  pagination.pageSize = size
  handleSearch()
}

// 当前页改变
const handleCurrentChange = (page) => {
  pagination.page = page
  handleSearch()
}

// 页面加载
onMounted(() => {
  handleSearch()
})
</script>

<style scoped>
.admin-management {
  padding: 20px;
}

.search-card,
.operation-card,
.table-card {
  margin-bottom: 20px;
}

.search-form {
  margin-bottom: 0;
}

.operation-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.operation-header h3 {
  margin: 0;
  color: #333;
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
