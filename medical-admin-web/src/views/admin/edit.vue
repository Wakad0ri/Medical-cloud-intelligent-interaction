<template>
  <div class="admin-edit">
    <el-card class="form-card">
      <template #header>
        <div class="card-header">
          <span>修改员工信息</span>
          <div>
            <el-tag v-if="adminForm.status === 1" type="warning" style="margin-right: 10px">
              该员工处于启用状态，需要先禁用才能修改信息
            </el-tag>
            <el-button type="text" @click="$router.back()">
              <el-icon><ArrowLeft /></el-icon>
              返回
            </el-button>
          </div>
        </div>
      </template>

      <el-form
        ref="adminFormRef"
        :model="adminForm"
        :rules="rules"
        label-width="120px"
        class="admin-form"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input
                v-model="adminForm.username"
                placeholder="请输入用户名"
                disabled
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="真实姓名" prop="realName">
              <el-input
                v-model="adminForm.realName"
                placeholder="请输入真实姓名"
                :disabled="adminForm.status === 1"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input
                v-model="adminForm.phone"
                placeholder="请输入手机号"
                :disabled="adminForm.status === 1"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input
                v-model="adminForm.email"
                placeholder="请输入邮箱"
                :disabled="adminForm.status === 1"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="身份证号" prop="idCard">
              <el-input
                v-model="adminForm.idCard"
                placeholder="请输入身份证号"
                :disabled="adminForm.status === 1"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="adminForm.gender" :disabled="adminForm.status === 1">
                <el-radio :label="1">男</el-radio>
                <el-radio :label="0">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input-number
                v-model="adminForm.age"
                :min="18"
                :max="100"
                placeholder="请输入年龄"
                style="width: 100%"
                :disabled="adminForm.status === 1"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="adminForm.status">
                <el-radio :label="1">启用</el-radio>
                <el-radio :label="0">禁用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="头像" prop="avatar">
          <ImageUpload v-model="adminForm.avatar" :disabled="adminForm.status === 1" />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="submitLoading" :disabled="adminForm.status === 1">
            保存修改
          </el-button>
          <el-button @click="handleReset">重置</el-button>
          <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { ArrowLeft } from '@element-plus/icons-vue'
import { updateAdmin } from '@/api/user'
import ImageUpload from '@/components/ImageUpload.vue'

const router = useRouter()
const route = useRoute()

// 表单引用
const adminFormRef = ref()

// 提交加载状态
const submitLoading = ref(false)

// 表单数据
const adminForm = reactive({
  id: '',
  username: '',
  realName: '',
  phone: '',
  email: '',
  idCard: '',
  gender: 1,
  age: '',
  avatar: '',
  status: 1
})

// 表单验证规则
const rules = {
  realName: [
    { required: true, message: '请输入真实姓名', trigger: 'blur' },
    { min: 2, max: 20, message: '姓名长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  idCard: [
    { pattern: /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, message: '请输入正确的身份证号', trigger: 'blur' }
  ],
  age: [
    { required: true, message: '请输入年龄', trigger: 'blur' },
    { type: 'number', min: 18, max: 100, message: '年龄必须在18-100之间', trigger: 'blur' }
  ]
}

// 提交表单
const handleSubmit = async () => {
  try {
    // 检查是否为启用状态
    if (adminForm.status === 1) {
      ElMessage.warning('该员工处于启用状态，无法修改信息')
      return
    }

    // 表单验证
    await adminFormRef.value.validate()

    submitLoading.value = true

    // 调用修改接口
    const response = await updateAdmin(adminForm)
    if (response.code === 1) {
      ElMessage.success('修改员工信息成功')
      router.back()
    } else {
      ElMessage.error(response.msg || '修改员工信息失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('修改员工信息失败:', error)
      if (error.msg) {
        ElMessage.error(`修改失败，${error.msg}`)
      } else {
        ElMessage.error('修改员工信息失败')
      }
    }
  } finally {
    submitLoading.value = false
  }
}

// 重置表单
const handleReset = () => {
  adminFormRef.value.resetFields()
}

// 页面加载时获取员工信息
onMounted(() => {
  // 从路由参数中获取员工信息
  if (route.query.admin) {
    try {
      const adminData = JSON.parse(decodeURIComponent(route.query.admin))
      // 只复制AdminUpdateDTO中包含的字段，排除password等字段
      adminForm.id = adminData.id
      adminForm.username = adminData.username
      adminForm.realName = adminData.realName
      adminForm.phone = adminData.phone
      adminForm.email = adminData.email
      adminForm.idCard = adminData.idCard
      adminForm.gender = adminData.gender
      adminForm.age = adminData.age
      adminForm.avatar = adminData.avatar || ''
      adminForm.status = adminData.status
    } catch (error) {
      console.error('解析员工信息失败:', error)
      ElMessage.error('获取员工信息失败')
      router.back()
    }
  } else {
    ElMessage.error('缺少员工信息')
    router.back()
  }
})
</script>

<style scoped>
.admin-edit {
  padding: 20px;
}

.form-card {
  max-width: 800px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  font-weight: bold;
}

.admin-form {
  padding: 20px 0;
}

.admin-form .el-form-item {
  margin-bottom: 24px;
}

.admin-form .el-input,
.admin-form .el-input-number {
  width: 100%;
}
</style>
