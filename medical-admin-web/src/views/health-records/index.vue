<template>
  <div class="health-records-page">
    <el-card>
      <template #header>
        <span>健康档案管理</span>
      </template>
      
      <div class="content">
        <el-empty description="健康档案管理功能开发中..." />
        <p>功能包括：</p>
        <ul>
          <li>患者健康档案查看</li>
          <li>病历记录管理</li>
          <li>检查报告上传</li>
          <li>健康数据统计</li>
        </ul>
      </div>
    </el-card>
  </div>
</template>

<script setup>
// 健康档案管理相关逻辑
</script>

<style scoped>
.health-records-page {
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
