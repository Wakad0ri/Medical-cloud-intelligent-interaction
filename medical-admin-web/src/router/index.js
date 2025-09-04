import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('@/layout/index.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { title: '首页', icon: 'House' }
      },
      {
        path: 'users',
        name: 'Users',
        component: () => import('@/views/users/index.vue'),
        meta: { title: '用户管理', icon: 'User' }
      },
      {
        path: 'doctors',
        name: 'Doctors',
        component: () => import('@/views/doctors/index.vue'),
        meta: { title: '医生管理', icon: 'Avatar' }
      },
      {
        path: 'departments',
        name: 'Departments',
        component: () => import('@/views/departments/index.vue'),
        meta: { title: '科室管理', icon: 'OfficeBuilding' }
      },
      {
        path: 'appointments',
        name: 'Appointments',
        component: () => import('@/views/appointments/index.vue'),
        meta: { title: '预约管理', icon: 'Calendar' }
      },
      {
        path: 'consultations',
        name: 'Consultations',
        component: () => import('@/views/consultations/index.vue'),
        meta: { title: 'AI问诊', icon: 'ChatDotRound' }
      },
      {
        path: 'health-records',
        name: 'HealthRecords',
        component: () => import('@/views/health-records/index.vue'),
        meta: { title: '健康档案', icon: 'Document' }
      },
      {
        path: 'statistics',
        name: 'Statistics',
        component: () => import('@/views/statistics/index.vue'),
        meta: { title: '数据统计', icon: 'DataAnalysis' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  
  if (to.path === '/login') {
    next()
  } else {
    if (userStore.token) {
      next()
    } else {
      next('/login')
    }
  }
})

export default router
