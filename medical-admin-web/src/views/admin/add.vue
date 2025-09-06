<template>
  <div class="add-admin">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>新增员工</span>
          <el-button type="text" @click="$router.back()">
            <el-icon><ArrowLeft /></el-icon>
            返回
          </el-button>
        </div>
      </template>

      <el-form
        ref="adminFormRef"
        :model="adminForm"
        :rules="adminRules"
        label-width="100px"
        class="admin-form"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input
                v-model="adminForm.username"
                placeholder="请输入用户名"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="密码" prop="password">
              <el-input
                v-model="adminForm.password"
                type="password"
                placeholder="请输入密码"
                show-password
                clearable
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="真实姓名" prop="realName">
              <el-input
                v-model="adminForm.realName"
                placeholder="请输入真实姓名"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input
                v-model="adminForm.phone"
                placeholder="请输入手机号"
                clearable
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
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input
                v-model="adminForm.email"
                placeholder="请输入邮箱"
                clearable
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-select v-model="adminForm.sex" placeholder="请选择性别" style="width: 100%">
                <el-option label="男" value="1" />
                <el-option label="女" value="0" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input-number
                v-model="adminForm.age"
                :min="18"
                :max="100"
                placeholder="请输入年龄"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="头像" prop="avatar">
          <el-input
            v-model="adminForm.avatar"
            placeholder="请输入头像URL（可选）"
            clearable
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="submitLoading">
            新增员工
          </el-button>
          <el-button @click="handleReset">重置</el-button>
          <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { ArrowLeft } from '@element-plus/icons-vue'
import { addAdmin } from '@/api/user'

const router = useRouter()

// 表单引用
const adminFormRef = ref()

// 提交加载状态
const submitLoading = ref(false)

// 表单数据
const adminForm = reactive({
  username: '',
  password: '',
  realName: '',
  phone: '',
  idCard: '',
  email: '',
  sex: '',
  age: null,
  avatar: ''
})

// 表单验证规则
const adminRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  realName: [
    { required: true, message: '请输入真实姓名', trigger: 'blur' },
    { min: 2, max: 10, message: '姓名长度在 2 到 10 个字符', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  idCard: [
    { required: true, message: '请输入身份证号', trigger: 'blur' },
    { pattern: /^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, message: '请输入正确的身份证号', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ],
  sex: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  age: [
    { required: true, message: '请输入年龄', trigger: 'blur' },
    { type: 'number', min: 18, max: 100, message: '年龄必须在 18 到 100 之间', trigger: 'blur' }
  ]
}

// 提交表单
const handleSubmit = async () => {
  if (!adminFormRef.value) return

  try {
    await adminFormRef.value.validate()
    submitLoading.value = true

    await addAdmin(adminForm)
    ElMessage.success('新增员工成功')
    router.back()
  } catch (error) {
    console.error('新增员工失败:', error)
    ElMessage.error(error.message || '新增员工失败')
  } finally {
    submitLoading.value = false
  }
}

// 重置表单
const handleReset = () => {
  adminFormRef.value?.resetFields()
}
</script>

<style scoped>
.add-admin {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.admin-form {
  max-width: 800px;
}

.admin-form .el-form-item {
  margin-bottom: 20px;
}
</style>
