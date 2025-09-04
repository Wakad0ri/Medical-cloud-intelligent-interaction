# 医云智能互动系统 - 管理端前端

## 项目简介

这是医云智能互动系统的管理端前端项目，基于 Vue 3 + Element Plus 构建的现代化管理后台。

## 技术栈

- **框架**: Vue 3.3.4
- **构建工具**: Vite 4.4.5
- **UI组件库**: Element Plus 2.3.8
- **状态管理**: Pinia 2.1.6
- **路由**: Vue Router 4.2.4
- **HTTP客户端**: Axios 1.4.0
- **图表库**: ECharts 5.4.3
- **样式预处理**: Sass

## 项目结构

```
medical-admin-web/
├── public/                 # 静态资源
├── src/
│   ├── api/               # API接口
│   ├── components/        # 公共组件
│   ├── layout/           # 布局组件
│   ├── router/           # 路由配置
│   ├── stores/           # 状态管理
│   ├── styles/           # 样式文件
│   ├── utils/            # 工具函数
│   ├── views/            # 页面组件
│   ├── App.vue           # 根组件
│   └── main.js           # 入口文件
├── index.html            # HTML模板
├── package.json          # 依赖配置
├── vite.config.js        # Vite配置
└── README.md            # 项目说明
```

## 功能模块

### 已实现功能
- ✅ 用户登录/退出
- ✅ 响应式布局
- ✅ 路由权限控制
- ✅ 用户管理（完整CRUD）
- ✅ 数据统计看板
- ✅ 统一错误处理

### 待开发功能
- 🚧 医生管理
- 🚧 科室管理
- 🚧 预约管理
- 🚧 AI问诊管理
- 🚧 健康档案管理
- 🚧 数据统计分析

## 快速开始

### 环境要求
- Node.js 16+
- npm 或 yarn

### 安装依赖
```bash
cd medical-admin-web
npm install
```

### 开发环境运行
```bash
npm run dev
```

### 生产环境构建
```bash
npm run build
```

### 预览构建结果
```bash
npm run preview
```

## 开发规范

### 目录命名
- 使用 kebab-case 命名文件夹
- 组件文件使用 PascalCase
- 页面文件使用 kebab-case

### 代码规范
- 使用 Composition API
- 使用 `<script setup>` 语法
- 统一使用 ES6+ 语法
- 组件 props 需要定义类型

### 样式规范
- 使用 scoped 样式
- 公共样式放在 styles 目录
- 使用 CSS 变量定义主题色

## API 接口

### 基础配置
- 基础URL: `/api`
- 超时时间: 10秒
- 请求头: `token` (登录后自动添加)

### 接口规范
```javascript
// 请求格式
{
  url: '/admin/user/login',
  method: 'post',
  data: { username, password, userType }
}

// 响应格式
{
  code: 200,
  message: '操作成功',
  data: { ... }
}
```

## 状态管理

使用 Pinia 进行状态管理，主要包括：

- `useUserStore`: 用户信息和登录状态
- 其他业务模块的状态管理

## 路由配置

- 使用 Vue Router 4
- 支持路由守卫
- 自动重定向到登录页

## 组件库

基于 Element Plus 构建，包含：
- 表单组件
- 表格组件
- 对话框组件
- 消息提示
- 图表组件

## 部署说明

### 开发环境
```bash
npm run dev
```
访问: http://localhost:3000

### 生产环境
```bash
npm run build
```
将 `dist` 目录部署到 Web 服务器

### Nginx 配置示例
```nginx
server {
    listen 80;
    server_name your-domain.com;
    
    location / {
        root /path/to/dist;
        try_files $uri $uri/ /index.html;
    }
    
    location /api {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## 默认账号

| 用户类型 | 用户名 | 密码 | 说明 |
|---------|--------|------|------|
| 管理员 | admin | 123456 | 系统管理员 |
| 医生 | doctor001 | 123456 | 测试医生 |

## 浏览器支持

- Chrome >= 87
- Firefox >= 78
- Safari >= 14
- Edge >= 88

## 贡献指南

1. Fork 项目
2. 创建特性分支
3. 提交更改
4. 推送到分支
5. 创建 Pull Request

## 许可证

MIT License
