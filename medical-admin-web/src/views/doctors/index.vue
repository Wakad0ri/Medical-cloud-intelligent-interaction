<template>
  <div class="doctors-page">
    <el-card>
      <template #header>
        <span>医生管理</span>
      </template>
      
      <div class="content">
        <el-empty description="医生管理功能开发中..." />
        <p>功能包括：</p>
        <ul>
          <li>医生信息管理</li>
          <li>医生排班管理</li>
          <li>医生专业特长设置</li>
          <li>医生工作统计</li>
        </ul>
      </div>
    </el-card>
  </div>
</template>

<script setup>
// 医生管理相关逻辑
</script>

<style scoped>
.doctors-page {
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
