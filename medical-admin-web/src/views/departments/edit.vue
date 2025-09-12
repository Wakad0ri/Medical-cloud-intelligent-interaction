<template>
  <div class="department-edit">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>编辑科室</span>
          <el-button @click="handleBack">返回</el-button>
        </div>
      </template>

      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="120px"
        style="max-width: 600px"
        v-loading="loading"
      >
        <el-form-item label="科室名称" prop="deptName">
          <el-input v-model="formData.deptName" placeholder="请输入科室名称" />
        </el-form-item>

        <el-form-item label="科室类型" prop="deptType">
          <el-select v-model="formData.deptType" placeholder="请选择科室类型" style="width: 100%">
            <el-option label="临床科室" :value="0" />
            <el-option label="医技科室" :value="1" />
            <el-option label="管理科室" :value="2" />
          </el-select>
        </el-form-item>

        <el-form-item label="上级科室" prop="parentId">
          <el-select v-model="formData.parentId" placeholder="请选择上级科室（可选）" style="width: 100%" clearable>
            <el-option
              v-for="dept in departmentOptions"
              :key="dept.id"
              :label="dept.deptName"
              :value="dept.id"
              :disabled="dept.id === formData.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="科室层级" prop="level">
          <el-input-number v-model="formData.level" :min="1" :max="5" placeholder="请输入科室层级" />
        </el-form-item>

        <el-form-item label="科室描述" prop="description">
          <el-input
            v-model="formData.description"
            type="textarea"
            :rows="3"
            placeholder="请输入科室描述"
          />
        </el-form-item>

        <el-form-item label="科室位置" prop="location">
          <el-input v-model="formData.location" placeholder="请输入科室位置" />
        </el-form-item>

        <el-form-item label="科室电话" prop="phone">
          <el-input v-model="formData.phone" placeholder="请输入科室电话" />
        </el-form-item>

        <el-form-item label="科室主任" prop="directorId">
          <el-select v-model="formData.directorId" placeholder="请选择科室主任（可选）" style="width: 100%" clearable>
            <el-option
              v-for="doctor in doctorOptions"
              :key="doctor.id"
              :label="doctor.doctorName"
              :value="doctor.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="床位数量" prop="bedCount">
          <el-input-number v-model="formData.bedCount" :min="0" placeholder="请输入床位数量" />
        </el-form-item>

        <el-form-item label="挂号费用" prop="consultationFee">
          <el-input-number
            v-model="formData.consultationFee"
            :min="0"
            :precision="2"
            placeholder="请输入挂号费用"
          />
        </el-form-item>

        <el-form-item label="排序" prop="sortOrder">
          <el-input-number v-model="formData.sortOrder" :min="0" placeholder="请输入排序值" />
        </el-form-item>

        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="formData.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="服务类型">
          <el-checkbox v-model="formData.isOutpatient" :true-label="1" :false-label="0">
            支持门诊
          </el-checkbox>
          <el-checkbox v-model="formData.isInpatient" :true-label="1" :false-label="0">
            支持住院
          </el-checkbox>
          <el-checkbox v-model="formData.isEmergency" :true-label="1" :false-label="0">
            支持急诊
          </el-checkbox>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="submitLoading">
            更新
          </el-button>
          <el-button @click="handleReset">重置</el-button>
          <el-button @click="handleBack">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { updateDepartment, getDepartmentById, getDepartmentOptions } from '@/api/department'
import { getDoctorOptions } from '@/api/doctor'

const router = useRouter()
const route = useRoute()

// 加载状态
const loading = ref(false)
const submitLoading = ref(false)

// 表单引用
const formRef = ref()

// 科室选项
const departmentOptions = ref([])

// 医生选项
const doctorOptions = ref([])

// 表单数据
const formData = reactive({
  id: null,
  deptName: '',
  deptType: null,
  parentId: null,
  level: 1,
  description: '',
  location: '',
  phone: '',
  directorId: null,
  bedCount: 0,
  consultationFee: 0,
  sortOrder: 0,
  status: 1,
  isOutpatient: 1,
  isInpatient: 0,
  isEmergency: 0
})

// 原始数据备份
const originalData = ref({})

// 表单验证规则
const formRules = {
  deptName: [
    { required: true, message: '请输入科室名称', trigger: 'blur' },
    { min: 2, max: 50, message: '科室名称长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  deptType: [
    { required: true, message: '请选择科室类型', trigger: 'change' }
  ],
  level: [
    { required: true, message: '请输入科室层级', trigger: 'blur' }
  ],
  phone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ]
}

// 获取科室详情
const getDepartmentDetail = async () => {
  const id = route.params.id
  if (!id) {
    ElMessage.error('科室ID不能为空')
    router.push('/admin/department')
    return
  }

  loading.value = true
  try {
    const response = await getDepartmentById(id)
    if (response.code === 1) {
      const data = response.data
      Object.assign(formData, {
        id: data.id,
        deptName: data.deptName,
        deptType: data.deptType,
        parentId: data.parentId,
        level: data.level,
        description: data.description,
        location: data.location,
        phone: data.phone,
        directorId: data.directorId,
        bedCount: data.bedCount,
        consultationFee: data.consultationFee,
        sortOrder: data.sortOrder,
        status: data.status,
        isOutpatient: data.isOutpatient,
        isInpatient: data.isInpatient,
        isEmergency: data.isEmergency
      })
      // 备份原始数据
      originalData.value = { ...formData }
    } else {
      ElMessage.error(response.msg || '获取科室详情失败')
      router.push('/admin/department')
    }
  } catch (error) {
    console.error('获取科室详情失败:', error)
    ElMessage.error('获取科室详情失败')
    router.push('/admin/department')
  } finally {
    loading.value = false
  }
}

// 获取科室选项
const getDepartmentOptionsList = async () => {
  try {
    const response = await getDepartmentOptions()
    if (response.code === 1) {
      departmentOptions.value = response.data || []
    }
  } catch (error) {
    console.error('获取科室选项失败:', error)
  }
}

// 获取医生选项
const getDoctorOptionsList = async () => {
  try {
    const response = await getDoctorOptions()
    if (response.code === 1) {
      doctorOptions.value = response.data || []
    }
  } catch (error) {
    console.error('获取医生选项失败:', error)
  }
}

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return

  try {
    await formRef.value.validate()
    submitLoading.value = true

    const response = await updateDepartment(formData)
    if (response.code === 1) {
      ElMessage.success('更新科室成功')
      router.push('/admin/department')
    } else {
      ElMessage.error(response.msg || '更新科室失败')
    }
  } catch (error) {
    console.error('更新科室失败:', error)
    ElMessage.error('更新科室失败')
  } finally {
    submitLoading.value = false
  }
}



// 重置表单
const handleReset = () => {
  Object.assign(formData, originalData.value)
  formRef.value?.clearValidate()
}

// 返回列表
const handleBack = () => {
  router.push('/admin/department')
}

// 页面加载
onMounted(() => {
  getDepartmentDetail()
  getDepartmentOptionsList()
  getDoctorOptionsList()
})
</script>

<style scoped>
.department-edit {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
