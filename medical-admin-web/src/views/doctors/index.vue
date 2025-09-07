<template>
  <div class="doctor-management">
    <!-- 搜索区域 -->
    <el-card class="search-card">
      <div class="search-container">
        <div class="search-left">
          <el-form :model="searchForm" :inline="true" class="search-form">
            <el-form-item label="医生姓名">
              <el-input
                v-model="searchForm.name"
                placeholder="请输入医生姓名"
                clearable
                style="width: 200px"
              />
            </el-form-item>
            <el-form-item label="科室">
              <el-select v-model="searchForm.departmentId" placeholder="请选择科室" style="width: 150px">
                <el-option label="全部" value="" />
                <el-option label="内科" value="1" />
                <el-option label="外科" value="2" />
                <el-option label="儿科" value="3" />
                <el-option label="妇产科" value="4" />
              </el-select>
            </el-form-item>
            <el-form-item label="账号状态">
              <el-select v-model="searchForm.status" placeholder="请选择账号状态" style="width: 150px">
                <el-option label="全部" value="" />
                <el-option label="在职" value="1" />
                <el-option label="离职" value="0" />
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
        <h3>医生列表</h3>
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
            新增医生
          </el-button>
        </div>
      </div>
    </el-card>

    <!-- 表格区域 -->
    <el-card class="table-card">
      <el-table
        :data="doctorList"
        v-loading="loading"
        stripe
        style="width: 100%"
        empty-text="暂无数据"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="doctorNo" label="医生编号" min-width="120" />
        <el-table-column prop="realName" label="医生姓名" min-width="120" />
        <el-table-column prop="username" label="账号" min-width="120" />
        <el-table-column prop="departmentName" label="科室" min-width="100" />
        <el-table-column prop="title" label="职称" min-width="100" />
        <el-table-column prop="phone" label="手机号" min-width="140" />
        <el-table-column prop="workYears" label="工作年限" width="100" align="center">
          <template #default="{ row }">
            {{ row.workYears || 0 }}年
          </template>
        </el-table-column>
        <el-table-column prop="accountStatus" label="账号状态" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="row.accountStatus === 1 ? 'success' : 'danger'">
              {{ row.accountStatus === 1 ? '在职' : '离职' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="workStatus" label="工作状态" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="row.workStatus === 1 ? 'success' : 'info'">
              {{ row.workStatus === 1 ? '在线' : '离线' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="最后操作时间" min-width="180">
          <template #default="{ row }">
            {{ row.updateTime || '暂无记录' }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="280" fixed="right" align="center">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">
              修改
            </el-button>
            <el-button type="info" size="small" @click="handleViewDetail(row)">
              详情
            </el-button>
            <el-button
              :type="row.accountStatus === 1 ? 'warning' : 'success'"
              size="small"
              @click="handleToggleStatus(row)"
              :loading="row.statusLoading"
            >
              {{ row.accountStatus === 1 ? '离职' : '入职' }}
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

    <!-- 医生详情对话框 -->
    <el-dialog
      v-model="detailDialogVisible"
      title="医生详情"
      width="600px"
      :close-on-click-modal="false"
    >
      <div v-if="currentDoctor" class="doctor-detail">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="医生编号">{{ currentDoctor.doctorNo }}</el-descriptions-item>
          <el-descriptions-item label="姓名">{{ currentDoctor.realName }}</el-descriptions-item>
          <el-descriptions-item label="用户名">{{ currentDoctor.username }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{ currentDoctor.gender === 1 ? '男' : '女' }}</el-descriptions-item>
          <el-descriptions-item label="年龄">{{ currentDoctor.age }}岁</el-descriptions-item>
          <el-descriptions-item label="手机号">{{ currentDoctor.phone }}</el-descriptions-item>
          <el-descriptions-item label="邮箱">{{ currentDoctor.email }}</el-descriptions-item>
          <el-descriptions-item label="身份证号">{{ currentDoctor.idCard }}</el-descriptions-item>
          <el-descriptions-item label="科室">{{ currentDoctor.departmentName }}</el-descriptions-item>
          <el-descriptions-item label="职称">{{ currentDoctor.title }}</el-descriptions-item>
          <el-descriptions-item label="工作年限">{{ currentDoctor.workYears }}年</el-descriptions-item>
          <el-descriptions-item label="账号状态">
            <el-tag :type="currentDoctor.accountStatus === 1 ? 'success' : 'danger'">
              {{ currentDoctor.accountStatus === 1 ? '在职' : '离职' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="专业特长" :span="2">
            {{ currentDoctor.speciality || '暂无' }}
          </el-descriptions-item>
          <el-descriptions-item label="医生简介" :span="2">
            {{ currentDoctor.introduction || '暂无' }}
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

const router = useRouter()

// 加载状态
const loading = ref(false)

// 搜索表单
const searchForm = reactive({
  name: '',
  departmentId: '',
  status: ''
})

// 分页信息
const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

// 医生列表
const doctorList = ref([])

// 选中的医生ID列表
const selectedIds = ref([])

// 详情对话框
const detailDialogVisible = ref(false)
const currentDoctor = ref(null)

// 模拟医生数据
const mockDoctorData = [
  {
    id: 1,
    doctorNo: 'DOC001',
    realName: '张医生',
    username: 'zhangdoc',
    departmentName: '内科',
    title: '主任医师',
    phone: '13800138001',
    workYears: 15,
    accountStatus: 1,
    workStatus: 1,
    gender: 1,
    age: 45,
    email: 'zhang@hospital.com',
    idCard: '110101197801011234',
    speciality: '心血管疾病诊治',
    introduction: '从事心血管疾病诊治15年，经验丰富',
    updateTime: '2024-01-15 10:30:00',
    statusLoading: false,
    deleteLoading: false
  },
  {
    id: 2,
    doctorNo: 'DOC002',
    realName: '李医生',
    username: 'lidoc',
    departmentName: '外科',
    title: '副主任医师',
    phone: '13800138002',
    workYears: 10,
    accountStatus: 1,
    workStatus: 0,
    gender: 0,
    age: 38,
    email: 'li@hospital.com',
    idCard: '110101198501011234',
    speciality: '普外科手术',
    introduction: '擅长各类普外科手术',
    updateTime: '2024-01-14 15:20:00',
    statusLoading: false,
    deleteLoading: false
  }
]

// 获取医生列表（模拟数据）
const getDoctorList = async () => {
  loading.value = true
  try {
    // 模拟API调用延迟
    await new Promise(resolve => setTimeout(resolve, 500))

    // 模拟搜索过滤
    let filteredData = [...mockDoctorData]

    if (searchForm.name) {
      filteredData = filteredData.filter(doctor =>
        doctor.realName.includes(searchForm.name)
      )
    }

    if (searchForm.departmentId) {
      filteredData = filteredData.filter(doctor =>
        doctor.departmentName === getDepartmentName(searchForm.departmentId)
      )
    }

    if (searchForm.status) {
      filteredData = filteredData.filter(doctor =>
        doctor.accountStatus.toString() === searchForm.status
      )
    }

    doctorList.value = filteredData
    pagination.total = filteredData.length

  } catch (error) {
    console.error('获取医生列表失败:', error)
    ElMessage.error('获取医生列表失败')
  } finally {
    loading.value = false
  }
}

// 获取科室名称（模拟）
const getDepartmentName = (id) => {
  const departments = {
    '1': '内科',
    '2': '外科',
    '3': '儿科',
    '4': '妇产科'
  }
  return departments[id] || '未知科室'
}

// 搜索
const handleSearch = () => {
  pagination.page = 1
  getDoctorList()
}

// 重置搜索
const handleReset = () => {
  Object.assign(searchForm, {
    name: '',
    departmentId: '',
    status: ''
  })
  handleSearch()
}

// 新增医生
const handleAdd = () => {
  ElMessage.info('新增医生功能开发中...')
  // router.push('/admin/doctors/add')
}

// 编辑医生
const handleEdit = (row) => {
  ElMessage.info('编辑医生功能开发中...')
  // router.push(`/admin/doctors/edit?id=${row.id}`)
}

// 查看详情
const handleViewDetail = (row) => {
  currentDoctor.value = row
  detailDialogVisible.value = true
}

// 切换状态
const handleToggleStatus = async (row) => {
  const newStatus = row.accountStatus === 1 ? 0 : 1
  const statusText = newStatus === 1 ? '入职' : '离职'

  try {
    await ElMessageBox.confirm(
      `确定要将医生"${row.realName}"设为${statusText}状态吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    row.statusLoading = true

    // 模拟API调用
    await new Promise(resolve => setTimeout(resolve, 1000))

    row.accountStatus = newStatus
    ElMessage.success(`${statusText}成功`)

  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(`${statusText}失败`)
    }
  } finally {
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
      `确定要删除医生"${row.realName}"吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    row.deleteLoading = true

    // 模拟API调用
    await new Promise(resolve => setTimeout(resolve, 1000))

    ElMessage.success('删除成功')
    getDoctorList()

  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  } finally {
    row.deleteLoading = false
  }
}

// 批量启用
const handleBatchEnable = async () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请选择要启用的医生')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要启用选中的 ${selectedIds.value.length} 个医生吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    ElMessage.success('批量启用成功')
    selectedIds.value = []
    getDoctorList()

  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('批量启用失败')
    }
  }
}

// 批量禁用
const handleBatchDisable = async () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请选择要禁用的医生')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要禁用选中的 ${selectedIds.value.length} 个医生吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    ElMessage.success('批量禁用成功')
    selectedIds.value = []
    getDoctorList()

  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('批量禁用失败')
    }
  }
}

// 批量删除
const handleBatchDelete = async () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请选择要删除的医生')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要删除选中的 ${selectedIds.value.length} 个医生吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    ElMessage.success('批量删除成功')
    selectedIds.value = []
    getDoctorList()

  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('批量删除失败')
    }
  }
}

// 分页大小改变
const handleSizeChange = (size) => {
  pagination.pageSize = size
  pagination.page = 1
  getDoctorList()
}

// 当前页改变
const handleCurrentChange = (page) => {
  pagination.page = page
  getDoctorList()
}

// 页面加载
onMounted(() => {
  getDoctorList()
})
</script>

<style scoped>
.doctor-management {
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

/* 新增医生按钮动画 */
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

/* 医生详情样式 */
.doctor-detail {
  padding: 20px 0;
}

.doctor-detail .el-descriptions {
  margin-top: 20px;
}

.doctor-detail .el-descriptions-item__label {
  font-weight: bold;
  color: #333;
}
</style>
