import { createRouter, createWebHistory } from 'vue-router'
import { useAdminStore } from '@/stores/admin'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'AdminLogin',
    component: () => import('@/views/login/index.vue'),
    meta: { title: '管理员登录' }
  },
  {
    path: '/doctor/login',
    name: 'DoctorLogin',
    component: () => import('@/views/login/doctor.vue'),
    meta: { title: '医生登录' }
  },
  {
    path: '/user/login',
    name: 'PatientLogin',
    component: () => import('@/views/login/patient.vue'),
    meta: { title: '患者登录' }
  },
  {
    path: '/admin',
    name: 'AdminLayout',
    component: () => import('@/views/admin/index.vue'),
    meta: { title: '管理员后台', requiresAuth: true },
    children: [
      {
        path: '',
        redirect: '/admin/dashboard'
      },
      {
        path: 'dashboard',
        name: 'AdminDashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { title: '管理员首页', requiresAuth: true }
      },
      {
        path: 'page',
        name: 'EmployeeManagement',
        component: () => import('@/views/admin/list.vue'),
        meta: { title: '员工管理', requiresAuth: true }
      },
      {
        path: 'add',
        name: 'AdminAdd',
        component: () => import('@/views/admin/add.vue'),
        meta: { title: '新增员工', requiresAuth: true }
      },
      {
        path: 'edit',
        name: 'EditEmployee',
        component: () => import('@/views/admin/edit.vue'),
        meta: { title: '修改员工', requiresAuth: true }
      },
      {
        path: 'doctors',
        name: 'DoctorManagement',
        component: () => import('@/views/doctors/index.vue'),
        meta: { title: '医生管理', requiresAuth: true }
      },
      {
        path: 'department',
        name: 'DepartmentManagement',
        component: () => import('@/views/departments/index.vue'),
        meta: { title: '科室管理', requiresAuth: true }
      },
      {
        path: 'department/add',
        name: 'DepartmentAdd',
        component: () => import('@/views/departments/add.vue'),
        meta: { title: '新增科室', requiresAuth: true }
      },
      {
        path: 'department/edit/:id',
        name: 'DepartmentEdit',
        component: () => import('@/views/departments/edit.vue'),
        meta: { title: '编辑科室', requiresAuth: true }
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
  const adminStore = useAdminStore()

  const loginPaths = ['/login', '/doctor/login', '/user/login']

  if (loginPaths.includes(to.path)) {
    // 如果已经登录，跳转到对应的主页
    if (adminStore.token) {
      next('/admin')
    } else {
      next()
    }
  } else {
    // 需要登录的页面
    if (adminStore.token) {
      next()
    } else {
      next('/login')
    }
  }
})

export default router
