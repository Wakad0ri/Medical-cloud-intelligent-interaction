<template>
  <div class="statistics-page">
    <el-card>
      <template #header>
        <span>数据统计</span>
      </template>
      
      <div class="content">
        <el-empty description="数据统计功能开发中..." />
        <p>功能包括：</p>
        <ul>
          <li>用户增长统计</li>
          <li>预约量统计</li>
          <li>AI问诊使用统计</li>
          <li>医生工作量统计</li>
          <li>收入统计分析</li>
        </ul>
      </div>
    </el-card>
  </div>
</template>

<script setup>
// 数据统计相关逻辑
</script>

<style scoped>
.statistics-page {
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
