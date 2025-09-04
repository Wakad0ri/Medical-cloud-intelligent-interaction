<template>
  <div class="dashboard">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :span="6">
        <el-card class="stats-card">
          <div class="stats-content">
            <div class="stats-icon user-icon">
              <el-icon><User /></el-icon>
            </div>
            <div class="stats-info">
              <h3>{{ stats.totalUsers }}</h3>
              <p>总用户数</p>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stats-card">
          <div class="stats-content">
            <div class="stats-icon doctor-icon">
              <el-icon><Avatar /></el-icon>
            </div>
            <div class="stats-info">
              <h3>{{ stats.totalDoctors }}</h3>
              <p>医生数量</p>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stats-card">
          <div class="stats-content">
            <div class="stats-icon appointment-icon">
              <el-icon><Calendar /></el-icon>
            </div>
            <div class="stats-info">
              <h3>{{ stats.todayAppointments }}</h3>
              <p>今日预约</p>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stats-card">
          <div class="stats-content">
            <div class="stats-icon consultation-icon">
              <el-icon><ChatDotRound /></el-icon>
            </div>
            <div class="stats-info">
              <h3>{{ stats.todayConsultations }}</h3>
              <p>今日问诊</p>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="charts-row">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>用户注册趋势</span>
          </template>
          <div ref="userTrendChart" style="height: 300px;"></div>
        </el-card>
      </el-col>
      
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>预约统计</span>
          </template>
          <div ref="appointmentChart" style="height: 300px;"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 快捷操作 -->
    <el-row :gutter="20" class="quick-actions">
      <el-col :span="24">
        <el-card>
          <template #header>
            <span>快捷操作</span>
          </template>
          <div class="actions-content">
            <el-button type="primary" @click="$router.push('/users')">
              <el-icon><User /></el-icon>
              用户管理
            </el-button>
            <el-button type="success" @click="$router.push('/doctors')">
              <el-icon><Avatar /></el-icon>
              医生管理
            </el-button>
            <el-button type="warning" @click="$router.push('/appointments')">
              <el-icon><Calendar /></el-icon>
              预约管理
            </el-button>
            <el-button type="info" @click="$router.push('/consultations')">
              <el-icon><ChatDotRound /></el-icon>
              AI问诊
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, nextTick } from 'vue'
import * as echarts from 'echarts'

const userTrendChart = ref()
const appointmentChart = ref()

// 统计数据
const stats = reactive({
  totalUsers: 1256,
  totalDoctors: 89,
  todayAppointments: 45,
  todayConsultations: 128
})

// 初始化用户趋势图表
const initUserTrendChart = () => {
  const chart = echarts.init(userTrendChart.value)
  const option = {
    title: {
      text: '最近7天用户注册'
    },
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    },
    yAxis: {
      type: 'value'
    },
    series: [{
      data: [12, 19, 15, 22, 18, 25, 20],
      type: 'line',
      smooth: true,
      itemStyle: {
        color: '#409EFF'
      }
    }]
  }
  chart.setOption(option)
}

// 初始化预约统计图表
const initAppointmentChart = () => {
  const chart = echarts.init(appointmentChart.value)
  const option = {
    title: {
      text: '科室预约分布'
    },
    tooltip: {
      trigger: 'item'
    },
    series: [{
      type: 'pie',
      radius: '50%',
      data: [
        { value: 35, name: '内科' },
        { value: 28, name: '外科' },
        { value: 20, name: '儿科' },
        { value: 15, name: '妇科' },
        { value: 12, name: '其他' }
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }]
  }
  chart.setOption(option)
}

onMounted(() => {
  nextTick(() => {
    initUserTrendChart()
    initAppointmentChart()
  })
})
</script>

<style scoped>
.dashboard {
  padding: 0;
}

.stats-row {
  margin-bottom: 20px;
}

.stats-card {
  height: 120px;
}

.stats-content {
  display: flex;
  align-items: center;
  height: 100%;
}

.stats-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
  font-size: 24px;
  color: white;
}

.user-icon {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.doctor-icon {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.appointment-icon {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.consultation-icon {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.stats-info h3 {
  margin: 0 0 5px 0;
  font-size: 28px;
  font-weight: bold;
  color: #333;
}

.stats-info p {
  margin: 0;
  color: #666;
  font-size: 14px;
}

.charts-row {
  margin-bottom: 20px;
}

.quick-actions {
  margin-bottom: 20px;
}

.actions-content {
  display: flex;
  gap: 15px;
  flex-wrap: wrap;
}

.actions-content .el-button {
  display: flex;
  align-items: center;
  gap: 5px;
}
</style>
