<template>
  <div class="consultations-page">
    <el-card>
      <template #header>
        <span>AI问诊管理</span>
      </template>
      
      <div class="content">
        <el-empty description="AI问诊管理功能开发中..." />
        <p>功能包括：</p>
        <ul>
          <li>AI对话记录查看</li>
          <li>问诊质量评估</li>
          <li>AI模型配置</li>
          <li>问诊数据分析</li>
        </ul>
      </div>
    </el-card>
  </div>
</template>

<script setup>
// AI问诊管理相关逻辑
</script>

<style scoped>
.consultations-page {
  padding: 0;
}

.content {
  text-align: center;
  padding: 40px;
}

.content ul {
  text-align: left;
  display: inline-block;
  margin-top: 20px;
}

.content li {
  margin-bottom: 8px;
  color: #666;
}
</style>
