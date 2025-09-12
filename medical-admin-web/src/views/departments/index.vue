<template>
  <div class="department-management">
    <!-- 搜索区域 -->
    <el-card class="search-card">
      <div class="search-container">
        <el-form :model="searchForm" :inline="true" class="search-form">
          <div class="search-row">
            <el-form-item label="科室名称">
              <el-input
                v-model="searchForm.deptName"
                placeholder="请输入科室名称"
                clearable
                style="width: 200px"
              />
            </el-form-item>
            <el-form-item label="科室状态">
              <el-select v-model="searchForm.status" placeholder="请选择状态" style="width: 150px">
                <el-option label="全部" value="" />
                <el-option label="启用" :value="1" />
                <el-option label="禁用" :value="0" />
              </el-select>
            </el-form-item>
            <el-form-item label="科室类型">
              <el-select v-model="searchForm.deptType" placeholder="请选择类型" style="width: 150px">
                <el-option label="全部" value="" />
                <el-option label="临床科室" :value="0" />
                <el-option label="医技科室" :value="1" />
                <el-option label="管理科室" :value="2" />
              </el-select>
            </el-form-item>
          </div>
          <div class="search-row">
            <el-form-item label="服务类型">
              <div class="service-checkboxes">
                <el-checkbox
                  v-model="searchForm.isOutpatient"
                  :true-label="1"
                  :false-label="''"
                  class="service-checkbox"
                  @change="handleSearch"
                >
                  <el-icon class="service-icon outpatient"><OfficeBuilding /></el-icon>
                  门诊
                </el-checkbox>
                <el-checkbox
                  v-model="searchForm.isInpatient"
                  :true-label="1"
                  :false-label="''"
                  class="service-checkbox"
                  @change="handleSearch"
                >
                  <el-icon class="service-icon inpatient"><House /></el-icon>
                  住院
                </el-checkbox>
                <el-checkbox
                  v-model="searchForm.isEmergency"
                  :true-label="1"
                  :false-label="''"
                  class="service-checkbox"
                  @change="handleSearch"
                >
                  <el-icon class="service-icon emergency"><Warning /></el-icon>
                  急诊
                </el-checkbox>
              </div>
            </el-form-item>
            <div class="search-buttons">
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
        </el-form>
      </div>
    </el-card>

    <!-- 操作区域 -->
    <el-card class="operation-card">
      <div class="operation-header">
        <h3>科室列表</h3>
        <div class="operation-buttons">
          <el-button
            type="primary"
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
          <el-button type="success" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            新增科室
          </el-button>
        </div>
      </div>
    </el-card>

    <!-- 表格区域 -->
    <el-card class="table-card">

      <el-table
        :data="departmentList"
        style="width: 100%"
        v-loading="loading"
        empty-text="暂无科室数据"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="deptCode" label="科室编码" width="120" />
        <el-table-column prop="deptName" label="科室名称" width="120">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleViewDetail(row)">
              {{ row.deptName }}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column prop="directorName" label="科室主任" width="150">
          <template #default="{ row }">
            {{ row.directorId === 0 || !row.directorName ? '该科室暂未任命主任' : row.directorName }}
          </template>
        </el-table-column>
        <el-table-column prop="location" label="科室位置" width="120" />
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column prop="bedCount" label="床位数" width="80" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="服务类型" width="120">
          <template #default="{ row }">
            <div class="service-tags">
              <el-tag v-if="row.isOutpatient" size="small" type="success">门诊</el-tag>
              <el-tag v-if="row.isInpatient" size="small" type="primary">住院</el-tag>
              <el-tag v-if="row.isEmergency" size="small" type="danger">急诊</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">
              编辑
            </el-button>
            <el-button
              size="small"
              :type="row.status === 1 ? 'warning' : 'success'"
              @click="handleStatusChange(row)"
            >
              {{ row.status === 1 ? '禁用' : '启用' }}
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

    <!-- 科室详情对话框 -->
    <el-dialog
      v-model="detailDialogVisible"
      title="科室详细信息"
      width="800px"
      :close-on-click-modal="false"
    >
      <div v-if="currentDepartment" class="department-detail">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="科室编码">
            {{ currentDepartment.deptCode }}
          </el-descriptions-item>
          <el-descriptions-item label="科室名称">
            {{ currentDepartment.deptName }}
          </el-descriptions-item>
          <el-descriptions-item label="科室类型">
            {{ getDeptTypeText(currentDepartment.deptType) }}
          </el-descriptions-item>
          <el-descriptions-item label="科室主任">
            {{ currentDepartment.directorId === 0 || !currentDepartment.directorName ? '该科室暂未任命主任' : currentDepartment.directorName }}
          </el-descriptions-item>
          <el-descriptions-item label="科室位置">
            {{ currentDepartment.location || '未填写' }}
          </el-descriptions-item>
          <el-descriptions-item label="联系电话">
            {{ currentDepartment.phone || '未填写' }}
          </el-descriptions-item>
          <el-descriptions-item label="床位数">
            {{ currentDepartment.bedCount || 0 }}
          </el-descriptions-item>
          <el-descriptions-item label="挂号费">
            {{ currentDepartment.consultationFee ? `￥${currentDepartment.consultationFee}` : '未设置' }}
          </el-descriptions-item>
          <el-descriptions-item label="科室状态" :span="2">
            <el-tag :type="getStatusType(currentDepartment.status)">
              {{ getStatusText(currentDepartment.status) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="服务类型" :span="2">
            <div class="service-tags">
              <el-tag v-if="currentDepartment.isOutpatient" size="small" type="success">门诊服务</el-tag>
              <el-tag v-if="currentDepartment.isInpatient" size="small" type="primary">住院服务</el-tag>
              <el-tag v-if="currentDepartment.isEmergency" size="small" type="danger">急诊服务</el-tag>
              <el-tag v-if="!currentDepartment.isOutpatient && !currentDepartment.isInpatient && !currentDepartment.isEmergency" size="small" type="info">暂无服务</el-tag>
            </div>
          </el-descriptions-item>
          <el-descriptions-item label="创建时间" :span="2">
            {{ formatDateTime(currentDepartment.createTime) }}
          </el-descriptions-item>
          <el-descriptions-item label="更新时间" :span="2">
            {{ formatDateTime(currentDepartment.updateTime) }}
          </el-descriptions-item>
          <el-descriptions-item label="科室描述" :span="2">
            <div class="description-text">
              {{ currentDepartment.description || '暂无描述' }}
            </div>
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="detailDialogVisible = false">关闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, Plus, Check, Close, Delete, OfficeBuilding, House, Warning } from '@element-plus/icons-vue'
import { getDepartmentPage, getDepartmentById, batchUpdateDepartmentStatus, batchDeleteDepartment } from '@/api/department'

const router = useRouter()

// 加载状态
const loading = ref(false)

// 详情对话框相关
const detailDialogVisible = ref(false)
const currentDepartment = ref(null)

// 搜索表单
const searchForm = reactive({
  deptName: '',
  status: '',
  deptType: '',
  isOutpatient: '',
  isInpatient: '',
  isEmergency: ''
})

// 分页信息
const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

// 科室列表
const departmentList = ref([])

// 选中的科室ID列表
const selectedIds = ref([])

// 获取科室类型文本
const getDeptTypeText = (type) => {
  const typeMap = {
    1: '临床科室',
    2: '医技科室',
    3: '行政科室'
  }
  return typeMap[type] || '未知'
}

// 获取状态文本
const getStatusText = (status) => {
  return status === 1 ? '启用' : '禁用'
}

// 获取状态标签类型
const getStatusType = (status) => {
  return status === 1 ? 'success' : 'danger'
}

// 获取科室列表
const getDepartmentList = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.page,
      pageSize: pagination.pageSize,
      deptName: searchForm.deptName || undefined,
      status: searchForm.status || undefined,
      deptType: searchForm.deptType || undefined,
      isOutpatient: searchForm.isOutpatient || undefined,
      isInpatient: searchForm.isInpatient || undefined,
      isEmergency: searchForm.isEmergency || undefined
    }

    const response = await getDepartmentPage(params)
    if (response.code === 1) {
      departmentList.value = response.data.records || []
      pagination.total = response.data.total || 0
    } else {
      ElMessage.error(response.msg || '获取科室列表失败')
    }
  } catch (error) {
    console.error('获取科室列表失败:', error)
    ElMessage.error('获取科室列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  pagination.page = 1
  getDepartmentList()
}

// 重置搜索
const handleReset = () => {
  Object.assign(searchForm, {
    deptName: '',
    status: '',
    deptType: '',
    isOutpatient: '',
    isInpatient: '',
    isEmergency: ''
  })
  handleSearch()
}

// 分页大小改变
const handleSizeChange = (size) => {
  pagination.pageSize = size
  pagination.page = 1
  getDepartmentList()
}

// 当前页改变
const handleCurrentChange = (page) => {
  pagination.page = page
  getDepartmentList()
}

// 查看详情
const handleViewDetail = async (row) => {
  try {
    const response = await getDepartmentById(row.id)
    if (response.code === 1) {
      currentDepartment.value = response.data
      detailDialogVisible.value = true
    } else {
      ElMessage.error(response.msg || '获取科室详情失败')
    }
  } catch (error) {
    console.error('获取科室详情失败:', error)
    ElMessage.error('获取科室详情失败')
  }
}

// 新增科室
const handleAdd = () => {
  router.push('/admin/department/add')
}

// 编辑科室
const handleEdit = (row) => {
  router.push(`/admin/department/edit/${row.id}`)
}

// 状态切换
const handleStatusChange = async (row) => {
  const newStatus = row.status === 1 ? 0 : 1
  const statusText = newStatus === 1 ? '启用' : '禁用'

  try {
    await ElMessageBox.confirm(
      `确定要${statusText}科室"${row.deptName}"吗？`,
      `${statusText}科室确认`,
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await batchUpdateDepartmentStatus(newStatus, [row.id])
    if (response.code === 1) {
      ElMessage.success(`${statusText}成功`)
      getDepartmentList()
    } else {
      ElMessage.error(response.msg || `${statusText}失败`)
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('状态切换失败:', error)
      ElMessage.error(`${statusText}失败`)
    }
  }
}

// 处理选择变化
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.id)
}

// 批量启用科室
const handleBatchEnable = async () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请选择要启用的科室')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要启用选中的 ${selectedIds.value.length} 个科室吗？`,
      '批量启用确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await batchUpdateDepartmentStatus(1, selectedIds.value)
    if (response.code === 1) {
      ElMessage.success('批量启用成功')
      selectedIds.value = []
      getDepartmentList()
    } else {
      ElMessage.error(response.msg || '批量启用失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量启用失败:', error)
      ElMessage.error('批量启用失败')
    }
  }
}

// 批量禁用科室
const handleBatchDisable = async () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请选择要禁用的科室')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要禁用选中的 ${selectedIds.value.length} 个科室吗？`,
      '批量禁用确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await batchUpdateDepartmentStatus(0, selectedIds.value)
    if (response.code === 1) {
      ElMessage.success('批量禁用成功')
      selectedIds.value = []
      getDepartmentList()
    } else {
      ElMessage.error(response.msg || '批量禁用失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量禁用失败:', error)
      ElMessage.error('批量禁用失败')
    }
  }
}

// 批量删除科室
const handleBatchDelete = async () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请选择要删除的科室')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要删除选中的 ${selectedIds.value.length} 个科室吗？删除后无法恢复！`,
      '批量删除确认',
      {
        confirmButtonText: '确定删除',
        cancelButtonText: '取消',
        type: 'error'
      }
    )

    const response = await batchDeleteDepartment(selectedIds.value)
    if (response.code === 1) {
      ElMessage.success('批量删除成功')
      selectedIds.value = []
      getDepartmentList()
    } else {
      ElMessage.error(response.msg || '批量删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量删除失败:', error)
      ElMessage.error('批量删除失败')
    }
  }
}

// 格式化日期时间
const formatDateTime = (dateTime) => {
  if (!dateTime) return '未知'
  const date = new Date(dateTime)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  })
}

// 页面加载
onMounted(() => {
  getDepartmentList()
})
</script>

<style scoped>
.department-management {
  padding: 20px;
}

.search-card {
  margin-bottom: 20px;
}

.search-container {
  width: 100%;
}

.search-form {
  margin-bottom: 0;
}

.search-row {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-bottom: 10px;
}

.search-row:last-child {
  margin-bottom: 0;
}

.search-buttons {
  display: flex;
  gap: 10px;
  margin-left: auto;
}

.service-checkboxes {
  display: flex;
  gap: 20px;
  align-items: center;
}

.service-checkbox {
  display: flex;
  align-items: center;
  padding: 8px 12px;
  border: 1px solid #dcdfe6;
  border-radius: 6px;
  background-color: #fff;
  transition: all 0.3s ease;
  cursor: pointer;
}

.service-checkbox:hover {
  border-color: #409eff;
  background-color: #f0f9ff;
}

.service-checkbox.is-checked {
  border-color: #409eff;
  background-color: #ecf5ff;
  color: #409eff;
}

.service-icon {
  margin-right: 6px;
  font-size: 16px;
}

.service-icon.outpatient {
  color: #67c23a;
}

.service-icon.inpatient {
  color: #409eff;
}

.service-icon.emergency {
  color: #f56c6c;
}

.operation-card {
  margin-bottom: 20px;
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

.table-card {
  margin-bottom: 20px;
}

.service-tags {
  display: flex;
  gap: 4px;
  flex-wrap: wrap;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.department-detail {
  max-height: 500px;
  overflow-y: auto;
}

.description-text {
  max-width: 400px;
  word-wrap: break-word;
  white-space: pre-wrap;
  line-height: 1.5;
}
</style>
