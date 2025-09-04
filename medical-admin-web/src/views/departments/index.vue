<template>
  <div class="departments-page">
    <el-card>
      <template #header>
        <span>科室管理</span>
      </template>
      
      <div class="content">
        <el-empty description="科室管理功能开发中..." />
        <p>功能包括：</p>
        <ul>
          <li>科室信息管理</li>
          <li>科室医生分配</li>
          <li>科室设备管理</li>
          <li>科室统计报表</li>
        </ul>
      </div>
    </el-card>
  </div>
</template>

<script setup>
// 科室管理相关逻辑
</script>

<style scoped>
.departments-page {
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
