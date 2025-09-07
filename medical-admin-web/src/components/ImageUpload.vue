<template>
  <div class="image-upload">
    <el-upload
      class="avatar-uploader"
      :action="uploadUrl"
      :headers="uploadHeaders"
      :show-file-list="false"
      :on-success="handleSuccess"
      :on-error="handleError"
      :before-upload="beforeUpload"
      :disabled="disabled"
    >
      <div v-if="imageUrl" class="avatar-preview">
        <img :src="imageUrl" class="avatar" alt="头像预览" />
        <div class="avatar-overlay">
          <el-icon class="avatar-icon"><Camera /></el-icon>
          <span>点击更换</span>
        </div>
      </div>
      <div v-else class="avatar-placeholder">
        <el-icon class="avatar-icon"><Plus /></el-icon>
        <div class="upload-text">点击上传头像</div>
      </div>
    </el-upload>

    <!-- 上传提示 -->
    <div class="upload-tips">
      <p>支持 JPG、PNG 格式，文件大小不超过 2MB</p>
      <p>建议尺寸：200x200 像素</p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { Camera, Plus } from '@element-plus/icons-vue'
import { useAdminStore } from '@/stores/admin'

// Props
const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  },
  disabled: {
    type: Boolean,
    default: false
  }
})

// Emits
const emit = defineEmits(['update:modelValue'])

// Store
const adminStore = useAdminStore()

// 上传配置
const uploadUrl = '/api/admin/common/upload'
const uploadHeaders = computed(() => ({
  'token': adminStore.token
}))

// 图片URL
const imageUrl = ref(props.modelValue)

// 监听外部值变化
watch(() => props.modelValue, (newVal) => {
  imageUrl.value = newVal
})

// 上传前验证
const beforeUpload = (file) => {
  // 检查文件类型
  const isImage = file.type === 'image/jpeg' || file.type === 'image/png'
  if (!isImage) {
    ElMessage.error('只能上传 JPG/PNG 格式的图片!')
    return false
  }

  // 检查文件大小
  const isLt2M = file.size / 1024 / 1024 < 2
  if (!isLt2M) {
    ElMessage.error('图片大小不能超过 2MB!')
    return false
  }

  return true
}

// 上传成功
const handleSuccess = (response) => {
  if (response.code === 1) {
    // 后端直接返回文件URL字符串
    imageUrl.value = response.data
    emit('update:modelValue', response.data)
    ElMessage.success('头像上传成功')
  } else {
    ElMessage.error(response.msg || '上传失败')
  }
}

// 上传失败
const handleError = (error) => {
  console.error('上传失败:', error)
  ElMessage.error('上传失败，请重试')
}
</script>

<style scoped>
.image-upload {
  text-align: center;
}

.avatar-uploader {
  display: inline-block;
}

.avatar-preview {
  position: relative;
  width: 120px;
  height: 120px;
  border: 2px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  overflow: hidden;
  transition: all 0.3s;
}

.avatar-preview:hover {
  border-color: #409eff;
}

.avatar-preview:hover .avatar-overlay {
  opacity: 1;
}

.avatar {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

.avatar-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.6);
  color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s;
}

.avatar-placeholder {
  width: 120px;
  height: 120px;
  border: 2px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  transition: all 0.3s;
  color: #8c939d;
}

.avatar-placeholder:hover {
  border-color: #409eff;
  color: #409eff;
}

.avatar-icon {
  font-size: 28px;
  margin-bottom: 8px;
}

.upload-text {
  font-size: 14px;
}

.upload-tips {
  margin-top: 10px;
  font-size: 12px;
  color: #999;
  line-height: 1.5;
}

.upload-tips p {
  margin: 2px 0;
}

/* 禁用状态 */
.avatar-uploader.is-disabled .avatar-preview,
.avatar-uploader.is-disabled .avatar-placeholder {
  cursor: not-allowed;
  opacity: 0.6;
}

.avatar-uploader.is-disabled .avatar-preview:hover,
.avatar-uploader.is-disabled .avatar-placeholder:hover {
  border-color: #d9d9d9;
  color: #8c939d;
}
</style>
