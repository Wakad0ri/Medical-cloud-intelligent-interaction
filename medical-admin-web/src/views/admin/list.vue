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
        <div class="operation-buttons">
          <el-button
            type="success"
            @click="handleBatchEnable"
            :disabled="selectedIds.length === 0"
          >
            <el-icon><Check /></el-icon>
            批量启用 ({{ selectedIds.length }})
          </el-button>
          <el-button
            type="warning"
            @click="handleBatchDisable"
            :disabled="selectedIds.length === 0"
          >
            <el-icon><Close /></el-icon>
            批量禁用 ({{ selectedIds.length }})
          </el-button>
          <el-button
            type="danger"
            @click="handleBatchDelete"
            :disabled="selectedIds.length === 0"
          >
            <el-icon><Delete /></el-icon>
            批量删除 ({{ selectedIds.length }})
          </el-button>
          <el-button type="primary" @click="handleAdd" class="add-btn">
            <el-icon><Plus /></el-icon>
            新增员工
          </el-button>
        </div>
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
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="realName" label="员工姓名" min-width="120">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleViewDetail(row)">
              {{ row.realName }}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column prop="username" label="账号" min-width="120" />
        <el-table-column prop="phone" label="手机号" min-width="140" />
        <el-table-column prop="status" label="账号状态" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="最后操作时间" min-width="180">
          <template #default="{ row }">
            {{ row.updateTime || '暂无记录' }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="240" fixed="right" align="center">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">
              修改
            </el-button>
            <el-button
              :type="row.status === 1 ? 'warning' : 'success'"
              size="small"
              @click="handleToggleStatus(row)"
              :loading="row.statusLoading"
            >
              {{ row.status === 1 ? '禁用' : '启用' }}
            </el-button>
            <el-button
              type="danger"
              size="small"
              @click="handleDelete(row)"
              :loading="row.deleteLoading"
            >
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

    <!-- 管理员详情对话框 -->
    <el-dialog
      v-model="detailDialogVisible"
      title="管理员详情"
      width="600px"
      :close-on-click-modal="false"
    >
      <div v-if="currentAdmin" class="admin-detail">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="员工姓名">
            {{ currentAdmin.realName || '暂无' }}
          </el-descriptions-item>
          <el-descriptions-item label="用户名">
            {{ currentAdmin.username || '暂无' }}
          </el-descriptions-item>
          <el-descriptions-item label="手机号">
            {{ currentAdmin.phone || '暂无' }}
          </el-descriptions-item>
          <el-descriptions-item label="邮箱">
            {{ currentAdmin.email || '暂无' }}
          </el-descriptions-item>
          <el-descriptions-item label="性别">
            {{ currentAdmin.gender === 1 ? '男' : currentAdmin.gender === 0 ? '女' : '暂无' }}
          </el-descriptions-item>
          <el-descriptions-item label="年龄">
            {{ currentAdmin.age || '暂无' }}
          </el-descriptions-item>
          <el-descriptions-item label="身份证号">
            {{ currentAdmin.idCard || '暂无' }}
          </el-descriptions-item>
          <el-descriptions-item label="账号状态">
            <el-tag :type="currentAdmin.status === 1 ? 'success' : 'danger'">
              {{ currentAdmin.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="创建时间" :span="2">
            {{ currentAdmin.createTime || '暂无' }}
          </el-descriptions-item>
          <el-descriptions-item label="更新时间" :span="2">
            {{ currentAdmin.updateTime || '暂无' }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="detailDialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, Plus, Delete, Check, Close } from '@element-plus/icons-vue'
import { getAdminPage, updateAdminStatus, deleteAdmin, batchUpdateAdminStatus } from '@/api/admin'

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

// 选中的员工ID列表
const selectedIds = ref([])

// 详情对话框相关
const detailDialogVisible = ref(false)
const currentAdmin = ref(null)

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
      // 为每个员工添加statusLoading和deleteLoading状态
      console.log('后端返回的数据:', response.data.records)
      adminList.value = response.data.records.map(admin => ({
        ...admin,
        statusLoading: false,
        deleteLoading: false
      }))
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

// 查看详情
const handleViewDetail = (row) => {
  currentAdmin.value = row
  detailDialogVisible.value = true
}

// 编辑员工
const handleEdit = (row) => {
  // 将员工信息作为查询参数传递到编辑页面
  const adminData = encodeURIComponent(JSON.stringify(row))
  router.push(`/admin/edit?admin=${adminData}`)
}

// 切换状态
const handleToggleStatus = async (row) => {
  console.log('点击的行数据:', row)
  console.log('row.id:', row.id, 'typeof:', typeof row.id)

  const newStatus = row.status === 1 ? 0 : 1
  const statusText = newStatus === 1 ? '启用' : '禁用'

  try {
    await ElMessageBox.confirm(
      `确定要${statusText}员工"${row.realName}"的账号吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    // 设置按钮加载状态
    row.statusLoading = true

    console.log('调用状态更新接口:', { id: row.id, status: newStatus, idType: typeof row.id, statusType: typeof newStatus })
    const response = await updateAdminStatus(newStatus, [row.id])
    console.log('后端响应:', response)
    if (response.code === 1) {
      // 更新本地状态
      row.status = newStatus
      ElMessage.success(`${statusText}成功`)
    } else {
      ElMessage.error(response.msg || `${statusText}失败`)
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('状态切换失败:', error)
      // 检查是否是后端返回的业务异常
      if (error.msg) {
        // 后端业务异常
        ElMessage.error(`${statusText}失败，${error.msg}`)
      } else if (error.response && error.response.data) {
        // HTTP错误
        if (error.response.data.msg) {
          ElMessage.error(`${statusText}失败，${error.response.data.msg}`)
        } else if (error.response.data.message) {
          ElMessage.error(`${statusText}失败，${error.response.data.message}`)
        } else {
          ElMessage.error(`${statusText}失败`)
        }
      } else {
        ElMessage.error(`${statusText}失败`)
      }
    }
  } finally {
    // 清除按钮加载状态
    row.statusLoading = false
  }
}

// 选择变化处理
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.id)
}

// 单个删除
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除员工"${row.realName}"吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    row.deleteLoading = true

    const response = await deleteAdmin([row.id])
    if (response.code === 1) {
      ElMessage.success('删除成功')
      getAdminList() // 重新加载列表
    } else {
      ElMessage.error(response.msg || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      if (error.msg) {
        ElMessage.error(error.msg)
      } else if (error.response && error.response.data && error.response.data.msg) {
        ElMessage.error(error.response.data.msg)
      } else {
        ElMessage.error('删除失败')
      }
    }
  } finally {
    row.deleteLoading = false
  }
}

// 批量启用
const handleBatchEnable = async () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请选择要启用的员工')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要启用选中的 ${selectedIds.value.length} 个员工吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await batchUpdateAdminStatus(selectedIds.value, 1)
    if (response.code === 1) {
      ElMessage.success('批量启用成功')
      selectedIds.value = [] // 清空选中状态
      getAdminList() // 重新加载列表
    } else {
      ElMessage.error(response.msg || '批量启用失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量启用失败:', error)
      if (error.msg) {
        ElMessage.error(error.msg)
      } else if (error.response && error.response.data && error.response.data.msg) {
        ElMessage.error(error.response.data.msg)
      } else {
        ElMessage.error('批量启用失败')
      }
    }
  }
}

// 批量禁用
const handleBatchDisable = async () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请选择要禁用的员工')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要禁用选中的 ${selectedIds.value.length} 个员工吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await batchUpdateAdminStatus(0, selectedIds.value)
    if (response.code === 1) {
      ElMessage.success('批量禁用成功')
      selectedIds.value = [] // 清空选中状态
      getAdminList() // 重新加载列表
    } else {
      ElMessage.error(response.msg || '批量禁用失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量禁用失败:', error)
      if (error.msg) {
        ElMessage.error(error.msg)
      } else if (error.response && error.response.data && error.response.data.msg) {
        ElMessage.error(error.response.data.msg)
      } else {
        ElMessage.error('批量禁用失败')
      }
    }
  }
}

// 批量删除
const handleBatchDelete = async () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请选择要删除的员工')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要删除选中的 ${selectedIds.value.length} 个员工吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await deleteAdmin(selectedIds.value)
    if (response.code === 1) {
      ElMessage.success('批量删除成功')
      selectedIds.value = [] // 清空选中状态
      getAdminList() // 重新加载列表
    } else {
      ElMessage.error(response.msg || '批量删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量删除失败:', error)
      if (error.msg) {
        ElMessage.error(error.msg)
      } else if (error.response && error.response.data && error.response.data.msg) {
        ElMessage.error(error.response.data.msg)
      } else {
        ElMessage.error('批量删除失败')
      }
    }
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

.operation-buttons {
  display: flex;
  gap: 10px;
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
