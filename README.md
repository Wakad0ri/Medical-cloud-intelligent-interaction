# 医云智能互动系统

## 项目简介

医云智能互动系统是一个基于Spring Boot 3.x的现代化医疗管理平台，集成了AI智能问诊、健康档案管理、预约挂号等核心功能。

## 技术栈

### 后端技术
- **框架**: Spring Boot 3.2.0
- **安全**: Spring Security
- **数据库**: MySQL 8.0
- **缓存**: Redis 7.x
- **ORM**: MyBatis-Plus 3.5.4
- **文档**: Knife4j (Swagger)
- **认证**: JWT

### 前端技术
- **管理端**: Vue 3 + Element Plus
- **用户端**: 微信小程序

### AI集成
- 百度文心一言API
- 阿里云通义千问API

## 项目结构

```
medical-cloud/
├── medical-common/          # 公共模块
│   ├── constant/           # 常量定义
│   ├── exception/          # 异常处理
│   ├── result/            # 统一返回结果
│   ├── utils/             # 工具类
│   └── properties/        # 配置属性
├── medical-pojo/           # 实体类模块
│   ├── entity/            # 实体类
│   ├── dto/               # 数据传输对象
│   └── vo/                # 视图对象
├── medical-server/         # 服务端模块
│   ├── controller/        # 控制器
│   ├── service/           # 服务层
│   ├── mapper/            # 数据访问层
│   ├── config/            # 配置类
│   └── interceptor/       # 拦截器
└── sql/                   # 数据库脚本
```

## 核心功能模块

### 1. 用户管理系统
- 用户注册/登录
- 角色权限管理
- 用户信息管理

### 2. 智能问诊系统
- AI智能对话
- 症状分析
- 初步诊断建议

### 3. 健康档案管理
- 个人健康档案
- 病历记录
- 检查报告管理

### 4. 预约挂号系统
- 科室医生查询
- 在线预约挂号
- 预约管理

### 5. AI健康助手
- 健康咨询
- 用药提醒
- 健康知识推送

### 6. 数据统计分析
- 用户数据统计
- 医疗数据分析
- 运营报表

## 快速开始

### 环境要求
- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 7.x+

### 安装步骤

1. **克隆项目**
```bash
git clone [项目地址]
cd medical-cloud
```

2. **数据库初始化**
```bash
# 执行数据库脚本
mysql -u root -p < sql/medical_cloud.sql
```

3. **配置文件修改**
```yaml
# 修改 medical-server/src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/medical_cloud
    username: your_username
    password: your_password
  data:
    redis:
      host: localhost
      port: 6379
```

4. **编译运行**
```bash
# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run -pl medical-server
```

5. **访问系统**
- 后端服务: http://localhost:8080
- API文档: http://localhost:8080/doc.html

## 默认账号

| 用户类型 | 用户名 | 密码 | 说明 |
|---------|--------|------|------|
| 管理员 | admin | 123456 | 系统管理员 |
| 医生 | doctor001 | 123456 | 测试医生 |
| 患者 | patient001 | 123456 | 测试患者 |

## API文档

项目集成了Knife4j，启动后访问 http://localhost:8080/doc.html 查看完整的API文档。

## 开发规范

### 代码规范
- 使用Lombok简化代码
- 统一异常处理
- 统一返回结果格式
- RESTful API设计

### 数据库规范
- 表名使用下划线命名
- 字段名使用下划线命名
- 必须有创建时间和更新时间
- 软删除使用状态字段

## 部署说明

### Docker部署
```bash
# 构建镜像
docker build -t medical-cloud .

# 运行容器
docker run -d -p 8080:8080 medical-cloud
```

### 生产环境配置
- 修改数据库连接配置
- 配置Redis连接
- 设置JWT密钥
- 配置AI服务API密钥

## 贡献指南

1. Fork 项目
2. 创建特性分支
3. 提交更改
4. 推送到分支
5. 创建 Pull Request

## 许可证

本项目采用 MIT 许可证。

## 联系方式

如有问题，请联系项目维护者。
