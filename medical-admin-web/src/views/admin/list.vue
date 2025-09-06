<template>
  <div class="admin-management">
    <!-- 搜索区域 -->
    <el-card class="search-card">
      <div class="search-container">
        <div class="search-left">
          <el-form :model="searchForm" :inline="true" class="search-form">
            <el-form-item label="员工姓名">
              <el-input
                v-model="searchForm.name"
                placeholder="请输入员工姓名"
                clearable
                style="width: 200px"
              />
            </el-form-item>
            <el-form-item label="账号状态">
              <el-select v-model="searchForm.status" placeholder="请选择账号状态" style="width: 150px">
                <el-option label="全部" value="" />
                <el-option label="启用" value="1" />
                <el-option label="禁用" value="0" />
              </el-select>
            </el-form-item>
          </el-form>
        </div>
        <div class="search-right">
          <el-button type="primary" @click="handleSearch" :loading="loading">
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button @click="handleReset">
            <el-icon><Refresh /></el-icon>
            重置
          </el-button>
        </div>
      </div>
    </el-card>

    <!-- 操作区域 -->
    <el-card class="operation-card">
      <div class="operation-header">
        <h3>员工列表</h3>
        <el-button type="primary" @click="handleAdd" class="add-btn">
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
        <el-table-column prop="realName" label="员工姓名" min-width="120" />
        <el-table-column prop="username" label="账号" min-width="100" />
        <el-table-column prop="phone" label="手机号" min-width="130" />
        <el-table-column prop="status" label="账号状态" min-width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="最后操作时间" min-width="160">
          <template #default="{ row }">
            {{ row.updateTime || '暂无记录' }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right" align="center">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">
              修改
            </el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">
              禁用
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
import { getAdminPage } from '@/api/user'

const router = useRouter()

// 加载状态
const loading = ref(false)

// 搜索表单
const searchForm = reactive({
  name: '',
  status: ''
})

// 分页信息
const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

// 员工列表
const adminList = ref([])

// 获取员工列表
const getAdminList = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.page,
      pageSize: pagination.pageSize,
      name: searchForm.name || undefined,
      status: searchForm.status || undefined
    }

    const response = await getAdminPage(params)
    if (response.code === 1) {
      adminList.value = response.data.records
      pagination.total = response.data.total
    } else {
      ElMessage.error(response.msg || '获取员工列表失败')
    }
  } catch (error) {
    console.error('获取员工列表失败:', error)
    ElMessage.error('获取员工列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  pagination.page = 1 // 重置到第一页
  getAdminList()
}

// 重置搜索
const handleReset = () => {
  Object.assign(searchForm, {
    name: '',
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
  pagination.page = 1 // 重置到第一页
  getAdminList()
}

// 当前页改变
const handleCurrentChange = (page) => {
  pagination.page = page
  getAdminList()
}

// 页面加载
onMounted(() => {
  getAdminList()
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

/* 搜索区域布局 */
.search-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.search-left {
  flex: 1;
}

.search-right {
  display: flex;
  gap: 10px;
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

/* 新增员工按钮动画 */
.add-btn {
  position: relative;
  overflow: hidden;
  transition: all 0.3s ease;
}

.add-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.4);
}

.add-btn:active {
  transform: translateY(0);
}

.add-btn::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
  transition: left 0.5s;
}

.add-btn:hover::before {
  left: 100%;
}
</style>
