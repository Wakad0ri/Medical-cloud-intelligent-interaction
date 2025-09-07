<template>
  <div class="test-page">
    <el-card>
      <template #header>
        <span>图片上传组件测试</span>
      </template>
      
      <div class="test-content">
        <h3>头像上传测试</h3>
        <ImageUpload v-model="avatarUrl" />
        
        <div class="result-display">
          <h4>上传结果：</h4>
          <p>图片URL: {{ avatarUrl || '暂无' }}</p>
          
          <div v-if="avatarUrl" class="preview-section">
            <h4>预览效果：</h4>
            <img :src="avatarUrl" alt="上传的图片" class="preview-image" />
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import ImageUpload from '@/components/ImageUpload.vue'

const avatarUrl = ref('')
</script>

<style scoped>
.test-page {
  padding: 20px;
}

.test-content {
  max-width: 600px;
}

.result-display {
  margin-top: 30px;
  padding: 20px;
  background: #f5f5f5;
  border-radius: 6px;
}

.preview-section {
  margin-top: 20px;
}

.preview-image {
  max-width: 200px;
  max-height: 200px;
  border: 1px solid #ddd;
  border-radius: 6px;
}
</style>
