<template>
  <div class="doctor-management">
    <!-- 搜索区域 -->
    <el-card class="search-card">
      <div class="search-container">
        <div class="search-left">
          <el-form :model="searchForm" inline>
            <el-form-item label="医生姓名">
              <el-input
                v-model="searchForm.realName"
                placeholder="请输入医生姓名"
                clearable
                style="width: 200px"
              />
            </el-form-item>
            <el-form-item label="科室">
              <el-select
                v-model="searchForm.departmentId"
                placeholder="请选择科室"
                style="width: 180px"
                clearable
              >
                <el-option label="全部" value="" />
                <el-option
                  v-for="dept in departmentOptions"
                  :key="dept.id"
                  :label="dept.deptName"
                  :value="dept.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="状态">
              <el-select v-model="searchForm.status" placeholder="请选择状态" style="width: 120px">
                <el-option label="全部" value="" />
                <el-option label="禁用" value="0" />
                <el-option label="正常" value="1" />
                <el-option label="工作" value="2" />
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
          <el-dropdown @command="handleBatchStatusChange" :disabled="selectedIds.length === 0">
            <el-button type="primary" :disabled="selectedIds.length === 0">
              <el-icon><Operation /></el-icon>
              批量状态操作 ({{ selectedIds.length }})
              <el-icon><ArrowDown /></el-icon>
            </el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="1">批量启用</el-dropdown-item>
                <el-dropdown-item command="2">批量设为工作</el-dropdown-item>
                <el-dropdown-item command="0">批量禁用</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
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
            新增医生
          </el-button>
        </div>
      </div>
    </el-card>

    <!-- 医生列表 -->
    <el-card class="table-card">
      <el-table
        :data="doctorList"
        style="width: 100%"
        v-loading="loading"
        empty-text="暂无医生数据"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="doctorNo" label="医生编号" width="120" />
        <el-table-column prop="realName" label="姓名" width="100">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleViewDetail(row)">
              {{ row.realName }}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column prop="gender" label="性别" width="80">
          <template #default="{ row }">
            {{ row.gender === 1 ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" width="80" />
        <el-table-column prop="phone" label="手机号" width="120" />
        <el-table-column prop="deptName" label="科室" width="120" />
        <el-table-column prop="title" label="职称" width="100" />
        <el-table-column prop="workYears" label="工作年限" width="100">
          <template #default="{ row }">
            {{ row.workYears || 0 }}年
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="isDirector" label="是否主任" width="100">
          <template #default="{ row }">
            <el-tag :type="row.isDirector ? 'success' : 'info'">
              {{ row.isDirector ? '是' : '否' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button
              type="primary"
              size="small"
              @click="handleEdit(row)"
              :disabled="row.status !== 0"
              :title="row.status !== 0 ? '只有禁用状态的医生才能编辑' : '编辑医生信息'"
            >
              编辑
            </el-button>
            <el-button
              size="small"
              type="primary"
              @click="handleStatusChange(row)"
            >
              {{ getNextStatusText(row.status) }}
            </el-button>
            <el-button
              type="danger"
              size="small"
              @click="handleDelete(row)"
              :disabled="row.status !== 0"
              :title="row.status !== 0 ? '只有禁用状态的医生才能删除' : '删除医生'"
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

    <!-- 新增/编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="600px"
      :before-close="handleDialogClose"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="formData.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item v-if="dialogType === 'add'" label="密码" prop="password">
          <el-input v-model="formData.password" type="password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item v-if="dialogType === 'edit'" label="更改密码">
          <el-checkbox v-model="changePassword" @change="handleChangePasswordToggle">
            更改密码
          </el-checkbox>
        </el-form-item>
        <el-form-item v-if="dialogType === 'edit' && changePassword" label="新密码" prop="newPassword">
          <el-input v-model="formData.newPassword" type="password" placeholder="请输入新密码" />
        </el-form-item>
        <el-form-item label="医生姓名" prop="realName">
          <el-input v-model="formData.realName" placeholder="请输入医生姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="formData.gender">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="formData.age" :min="18" :max="80" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="formData.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="formData.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="formData.idCard" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="科室" prop="departmentId">
          <el-select v-model="formData.departmentId" placeholder="请选择科室" style="width: 100%">
            <el-option
              v-for="dept in departmentOptions"
              :key="dept.id"
              :label="dept.deptName"
              :value="dept.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="职称" prop="title">
          <el-input v-model="formData.title" placeholder="请输入职称" />
        </el-form-item>
        <el-form-item label="专业特长" prop="specialty">
          <el-input v-model="formData.specialty" type="textarea" placeholder="请输入专业特长" />
        </el-form-item>
        <el-form-item label="工作年限" prop="workYears">
          <el-input-number v-model="formData.workYears" :min="0" :max="50" />
        </el-form-item>
        <el-form-item label="医生简介" prop="introduction">
          <el-input v-model="formData.introduction" type="textarea" placeholder="请输入医生简介" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="handleDialogClose">取消</el-button>
          <el-button type="primary" @click="handleSubmit" :loading="submitLoading">
            确认
          </el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 医生详情对话框 -->
    <el-dialog
      v-model="detailDialogVisible"
      title="医生详细信息"
      width="600px"
      :close-on-click-modal="false"
    >
      <div v-if="currentDoctor" class="doctor-detail">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="医生编号">
            {{ currentDoctor.doctorNo }}
          </el-descriptions-item>
          <el-descriptions-item label="用户名">
            {{ currentDoctor.username }}
          </el-descriptions-item>
          <el-descriptions-item label="真实姓名">
            {{ currentDoctor.realName }}
          </el-descriptions-item>
          <el-descriptions-item label="性别">
            {{ currentDoctor.gender === 1 ? '男' : '女' }}
          </el-descriptions-item>
          <el-descriptions-item label="年龄">
            {{ currentDoctor.age }}
          </el-descriptions-item>
          <el-descriptions-item label="手机号">
            {{ currentDoctor.phone }}
          </el-descriptions-item>
          <el-descriptions-item label="邮箱">
            {{ currentDoctor.email || '未填写' }}
          </el-descriptions-item>
          <el-descriptions-item label="身份证号">
            {{ currentDoctor.idCard }}
          </el-descriptions-item>
          <el-descriptions-item label="所属科室">
            {{ getDepartmentName(currentDoctor.departmentId) }}
          </el-descriptions-item>
          <el-descriptions-item label="职称">
            {{ currentDoctor.title || '未填写' }}
          </el-descriptions-item>
          <el-descriptions-item label="专业特长">
            {{ currentDoctor.specialty || '未填写' }}
          </el-descriptions-item>
          <el-descriptions-item label="工作年限">
            {{ currentDoctor.workYears || '未填写' }}年
          </el-descriptions-item>
          <el-descriptions-item label="账号状态" :span="2">
            <el-tag :type="getStatusType(currentDoctor.status)">
              {{ getStatusText(currentDoctor.status) }}
            </el-tag>
          </el-descriptions-item>

          <el-descriptions-item label="创建时间" :span="2">
            {{ formatDateTime(currentDoctor.createTime) }}
          </el-descriptions-item>
          <el-descriptions-item label="更新时间" :span="2">
            {{ formatDateTime(currentDoctor.updateTime) }}
          </el-descriptions-item>
          <el-descriptions-item label="创建人">
            {{ getAdminName(currentDoctor.createAdmin) }}
          </el-descriptions-item>
          <el-descriptions-item label="更新人">
            {{ getAdminName(currentDoctor.updateAdmin) }}
          </el-descriptions-item>
          <el-descriptions-item label="医生简介" :span="2">
            <div class="introduction-text">
              {{ currentDoctor.introduction || '暂无简介' }}
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
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, Plus, Delete, Operation, ArrowDown } from '@element-plus/icons-vue'
import {
  getDoctorPage,
  addDoctor,
  updateDoctor,
  updateDoctorStatus,
  deleteDoctors,
  getDoctorById
} from '@/api/doctor'
import { getDepartmentOptions } from '@/api/department'
import { getAdminOptions } from '@/api/admin'

// 加载状态
const loading = ref(false)

// 搜索表单
const searchForm = reactive({
  realName: '',
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

// 科室选项
const departmentOptions = ref([])

// 管理员选项
const adminOptions = ref([])

// 对话框相关
const dialogVisible = ref(false)
const dialogType = ref('add') // 'add' | 'edit'
const dialogTitle = computed(() => dialogType.value === 'add' ? '新增医生' : '编辑医生')
const submitLoading = ref(false)

// 详情对话框相关
const detailDialogVisible = ref(false)
const currentDoctor = ref(null)

// 密码更改相关
const changePassword = ref(false)

// 表单相关
const formRef = ref()
const formData = reactive({
  id: null,
  username: '',
  password: '',
  newPassword: '',
  realName: '',
  gender: 1,
  age: null,
  phone: '',
  email: '',
  idCard: '',
  departmentId: null,
  title: '',
  specialty: '',
  workYears: null,
  introduction: ''
})

// 表单验证规则
const formRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  realName: [
    { required: true, message: '请输入医生姓名', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  age: [
    { required: true, message: '请输入年龄', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ],
  departmentId: [
    { required: true, message: '请选择科室', trigger: 'change' }
  ]
}

// 获取医生列表
const getDoctorList = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.page,
      pageSize: pagination.pageSize,
      realName: searchForm.realName || undefined,
      departmentId: searchForm.departmentId || undefined,
      status: searchForm.status || undefined
    }

    const response = await getDoctorPage(params)
    if (response.code === 1) {
      doctorList.value = response.data.records || []
      pagination.total = response.data.total || 0
    } else {
      ElMessage.error(response.msg || '获取医生列表失败')
    }
  } catch (error) {
    console.error('获取医生列表失败:', error)
    ElMessage.error('获取医生列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  pagination.page = 1
  getDoctorList()
}

// 重置搜索
const handleReset = () => {
  Object.assign(searchForm, {
    realName: '',
    departmentId: '',
    status: ''
  })
  handleSearch()
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

// 状态相关辅助方法
const getStatusText = (status) => {
  switch (status) {
    case 0: return '禁用'
    case 1: return '正常'
    case 2: return '工作'
    default: return '未知'
  }
}

const getStatusType = (status) => {
  switch (status) {
    case 0: return 'danger'
    case 1: return 'success'
    case 2: return 'warning'
    default: return 'info'
  }
}

// 获取科室选项
const loadDepartmentOptions = async () => {
  try {
    const response = await getDepartmentOptions()
    if (response.code === 1) {
      departmentOptions.value = response.data || []
    } else {
      console.error('获取科室选项失败:', response.msg)
    }
  } catch (error) {
    console.error('获取科室选项失败:', error)
  }
}

// 获取管理员选项
const loadAdminOptions = async () => {
  try {
    const response = await getAdminOptions()
    if (response.code === 1) {
      adminOptions.value = response.data || []
    } else {
      console.error('获取管理员选项失败:', response.msg)
    }
  } catch (error) {
    console.error('获取管理员选项失败:', error)
  }
}

// 重置表单
const resetForm = () => {
  Object.assign(formData, {
    id: null,
    username: '',
    password: '',
    realName: '',
    gender: 1,
    age: null,
    phone: '',
    email: '',
    idCard: '',
    departmentId: null,
    title: '',
    specialty: '',
    workYears: null,
    introduction: ''
  })
  formRef.value?.clearValidate()
}

// 查看医生详情
const handleViewDetail = async (row) => {
  try {
    const response = await getDoctorById(row.id)
    if (response.code === 1) {
      currentDoctor.value = response.data
      detailDialogVisible.value = true
    } else {
      ElMessage.error(response.msg || '获取医生详情失败')
    }
  } catch (error) {
    console.error('获取医生详情失败:', error)
    ElMessage.error('获取医生详情失败')
  }
}

// 新增医生
const handleAdd = () => {
  dialogType.value = 'add'
  resetForm()
  dialogVisible.value = true
}

// 编辑医生
const handleEdit = async (row) => {
  dialogType.value = 'edit'
  try {
    const response = await getDoctorById(row.id)
    if (response.code === 1) {
      const doctor = response.data
      // 只提取可修改的字段，避免包含不可修改的字段如doctorNo
      Object.assign(formData, {
        id: doctor.id,
        username: doctor.username,
        realName: doctor.realName,
        gender: doctor.gender,
        age: doctor.age,
        phone: doctor.phone,
        email: doctor.email,
        idCard: doctor.idCard,
        departmentId: doctor.departmentId,
        title: doctor.title,
        specialty: doctor.specialty,
        workYears: doctor.workYears,
        introduction: doctor.introduction
      })
      dialogVisible.value = true
    } else {
      ElMessage.error(response.msg || '获取医生信息失败')
    }
  } catch (error) {
    console.error('获取医生信息失败:', error)
    ElMessage.error('获取医生信息失败')
  }
}

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return

  try {
    await formRef.value.validate()
    submitLoading.value = true

    const submitData = { ...formData }

    if (dialogType.value === 'add') {
      delete submitData.id
      delete submitData.newPassword
      const response = await addDoctor(submitData)
      if (response.code === 1) {
        ElMessage.success('新增医生成功')
        dialogVisible.value = false
        getDoctorList()
      } else {
        ElMessage.error(response.msg || '新增医生失败')
      }
    } else {
      // 编辑模式
      delete submitData.password

      // 如果选择更改密码，将新密码赋值给password字段
      if (changePassword.value && submitData.newPassword) {
        submitData.password = submitData.newPassword
      }
      delete submitData.newPassword

      const response = await updateDoctor(submitData)
      if (response.code === 1) {
        ElMessage.success('更新医生成功')
        dialogVisible.value = false
        getDoctorList()
      } else {
        ElMessage.error(response.msg || '更新医生失败')
      }
    }
  } catch (error) {
    console.error('提交失败:', error)
    // 检查是否是后端返回的业务异常
    if (error.msg) {
      // 后端业务异常
      ElMessage.error(error.msg)
    } else if (error.response && error.response.data) {
      // HTTP错误
      if (error.response.data.msg) {
        ElMessage.error(error.response.data.msg)
      } else if (error.response.data.message) {
        ElMessage.error(error.response.data.message)
      } else {
        ElMessage.error('操作失败')
      }
    } else {
      ElMessage.error('操作失败')
    }
  } finally {
    submitLoading.value = false
  }
}

// 关闭对话框
const handleDialogClose = () => {
  dialogVisible.value = false
  resetForm()
}

// 获取下一个状态的按钮文本
const getNextStatusText = (currentStatus) => {
  switch (currentStatus) {
    case 0: return '启用'      // 禁用 → 启用
    case 1: return '工作'      // 启用 → 工作
    case 2: return '禁用'      // 工作 → 禁用
    default: return '启用'
  }
}

// 获取下一个状态值
const getNextStatus = (currentStatus) => {
  switch (currentStatus) {
    case 0: return 1  // 禁用 → 启用
    case 1: return 2  // 启用 → 工作
    case 2: return 0  // 工作 → 禁用
    default: return 1
  }
}

// 状态切换
const handleStatusChange = async (row) => {
  const newStatus = getNextStatus(row.status)
  const statusText = getNextStatusText(row.status)

  try {
    const response = await updateDoctorStatus(newStatus, [row.id])
    if (response.code === 1) {
      ElMessage.success(`${statusText}成功`)
      getDoctorList()
    } else {
      ElMessage.error(response.msg || `${statusText}失败`)
    }
  } catch (error) {
    console.error(`${statusText}失败:`, error)
    // 检查是否是后端返回的业务异常
    if (error.msg) {
      // 后端业务异常
      ElMessage.error(`${statusText}失败：${error.msg}`)
    } else if (error.response && error.response.data) {
      // HTTP错误
      if (error.response.data.msg) {
        ElMessage.error(`${statusText}失败：${error.response.data.msg}`)
      } else if (error.response.data.message) {
        ElMessage.error(`${statusText}失败：${error.response.data.message}`)
      } else {
        ElMessage.error(`${statusText}失败`)
      }
    } else {
      ElMessage.error(`${statusText}失败`)
    }
  }
}

// 删除医生
const handleDelete = (row) => {
  ElMessageBox.confirm(
    `确定要删除医生 "${row.realName}" 吗？`,
    '删除确认',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      const response = await deleteDoctors([row.id])
      if (response.code === 1) {
        ElMessage.success('删除成功')
        getDoctorList()
      } else {
        ElMessage.error(response.msg || '删除失败')
      }
    } catch (error) {
      console.error('删除失败:', error)
      // 检查是否是后端返回的业务异常
      if (error.msg) {
        // 后端业务异常
        ElMessage.error(`删除失败：${error.msg}`)
      } else if (error.response && error.response.data) {
        // HTTP错误
        if (error.response.data.msg) {
          ElMessage.error(`删除失败：${error.response.data.msg}`)
        } else if (error.response.data.message) {
          ElMessage.error(`删除失败：${error.response.data.message}`)
        } else {
          ElMessage.error('删除失败')
        }
      } else {
        ElMessage.error('删除失败')
      }
    }
  }).catch(() => {
    // 用户取消删除
  })
}

// 选择变化处理
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.id)
}

// 批量状态变更
const handleBatchStatusChange = async (status) => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请先选择要操作的医生')
    return
  }

  const statusMap = {
    0: '禁用',
    1: '启用',
    2: '设为工作'
  }
  const statusText = statusMap[status]

  try {
    await ElMessageBox.confirm(
      `确定要${statusText}选中的 ${selectedIds.value.length} 个医生吗？`,
      '确认操作',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await updateDoctorStatus(parseInt(status), selectedIds.value)
    if (response.code === 1) {
      ElMessage.success(`批量${statusText}成功`)
      selectedIds.value = [] // 清空选中状态
      getDoctorList() // 重新加载列表
    } else {
      ElMessage.error(response.msg || `批量${statusText}失败`)
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error(`批量${statusText}失败:`, error)
      if (error.msg) {
        ElMessage.error(`批量${statusText}失败：${error.msg}`)
      } else if (error.response && error.response.data && error.response.data.msg) {
        ElMessage.error(`批量${statusText}失败：${error.response.data.msg}`)
      } else {
        ElMessage.error(`批量${statusText}失败`)
      }
    }
  }
}

// 批量删除
const handleBatchDelete = async () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请先选择要删除的医生')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要删除选中的 ${selectedIds.value.length} 个医生吗？`,
      '确认删除',
      {
        confirmButtonText: '确定删除',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await deleteDoctors(selectedIds.value)
    if (response.code === 1) {
      ElMessage.success('批量删除成功')
      selectedIds.value = [] // 清空选中状态
      getDoctorList() // 重新加载列表
    } else {
      ElMessage.error(response.msg || '批量删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量删除失败:', error)
      if (error.msg) {
        ElMessage.error(`批量删除失败：${error.msg}`)
      } else if (error.response && error.response.data && error.response.data.msg) {
        ElMessage.error(`批量删除失败：${error.response.data.msg}`)
      } else {
        ElMessage.error('批量删除失败')
      }
    }
  }
}

// 根据科室ID获取科室名称
const getDepartmentName = (departmentId) => {
  const department = departmentOptions.value.find(dept => dept.id === departmentId)
  return department ? department.deptName : '未知科室'
}

// 根据管理员ID获取管理员姓名
const getAdminName = (adminId) => {
  if (!adminId) return '系统'
  const admin = adminOptions.value.find(admin => admin.id === adminId)
  return admin ? admin.adminName : `管理员${adminId}`
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
  loadDepartmentOptions()
  loadAdminOptions()
  getDoctorList()
})
</script>

<style scoped>
.doctor-management {
  padding: 20px;
}

.search-card {
  margin-bottom: 20px;
}

.search-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-left {
  flex: 1;
}

.search-right {
  margin-left: 20px;
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
  color: #303133;
}

.operation-buttons {
  display: flex;
  gap: 10px;
}

.table-card {
  margin-bottom: 20px;
}

.table-header {
  margin-bottom: 20px;
}

.table-header h3 {
  margin: 0;
  color: #303133;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.doctor-detail {
  max-height: 500px;
  overflow-y: auto;
}

.introduction-text {
  max-width: 400px;
  word-wrap: break-word;
  white-space: pre-wrap;
  line-height: 1.5;
}
</style>
