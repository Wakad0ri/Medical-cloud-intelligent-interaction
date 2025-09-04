# API文档访问指南

## 概述

医云智能互动系统使用 **Knife4j** 和 **SpringDoc** 生成API文档，提供了美观易用的接口文档界面。

## 访问地址

启动后端服务后，可以通过以下地址访问API文档：

### Knife4j 文档界面（推荐）
- **地址**: http://localhost:8080/doc.html
- **特点**: 界面美观，功能丰富，支持在线测试

### Swagger UI 文档界面
- **地址**: http://localhost:8080/swagger-ui.html
- **特点**: 标准Swagger界面，简洁实用

### OpenAPI JSON
- **地址**: http://localhost:8080/v3/api-docs
- **用途**: 获取原始的OpenAPI 3.0规范JSON数据

## API分组

系统将API按功能模块分为以下几个组：

### 1. 管理端接口
- **路径**: `/admin/**`
- **包含**: 用户管理、医生管理、科室管理等管理功能
- **权限**: 需要管理员或医生权限

### 2. 用户端接口
- **路径**: `/user/**`
- **包含**: 用户登录、预约挂号、AI问诊等用户功能
- **权限**: 需要用户登录

### 3. 公共接口
- **路径**: `/api/**`, `/common/**`
- **包含**: 不需要认证的公共接口
- **权限**: 无需认证

## 接口认证

### 管理端接口
- **请求头**: `token`
- **获取方式**: 通过管理端登录接口获取
- **示例**: `token: eyJhbGciOiJIUzI1NiJ9...`

### 用户端接口
- **请求头**: `authentication`
- **获取方式**: 通过用户端登录接口获取
- **示例**: `authentication: eyJhbGciOiJIUzI1NiJ9...`

## 使用步骤

### 1. 启动后端服务
```bash
cd medical-server
mvn spring-boot:run
```

### 2. 访问文档界面
打开浏览器访问: http://localhost:8080/doc.html

### 3. 选择API分组
在文档界面左上角选择要查看的API分组

### 4. 测试接口
1. 点击要测试的接口
2. 填写请求参数
3. 如需认证，先调用登录接口获取token
4. 在右上角"文档管理" -> "全局参数设置"中添加token
5. 点击"发送"按钮测试接口

## 常用接口

### 管理端登录
- **接口**: `POST /admin/user/login`
- **参数**: 
  ```json
  {
    "username": "admin",
    "password": "123456",
    "userType": 3
  }
  ```

### 用户端登录
- **接口**: `POST /user/user/login`
- **参数**: 
  ```json
  {
    "username": "patient001",
    "password": "123456",
    "userType": 1
  }
  ```

## 配置说明

### SpringDoc配置
```yaml
springdoc:
  api-docs:
    enabled: true
    path: /v3/api-docs
  swagger-ui:
    enabled: true
    path: /swagger-ui.html
```

### Knife4j配置
```yaml
knife4j:
  enable: true
  setting:
    language: zh_cn
    swagger-model-name: 实体类列表
```

## 注意事项

1. **开发环境**: 文档功能默认开启
2. **生产环境**: 建议关闭文档功能以提高安全性
3. **接口测试**: 建议使用Knife4j界面进行接口测试
4. **权限控制**: 需要认证的接口必须先获取token

## 故障排除

### 1. 无法访问文档页面
- 检查后端服务是否正常启动
- 确认端口号是否正确（默认8080）
- 检查防火墙设置

### 2. 接口测试失败
- 检查请求参数是否正确
- 确认是否已添加认证token
- 查看后端日志排查错误

### 3. 文档显示不完整
- 检查Controller类是否添加了正确的注解
- 确认包扫描路径是否正确
- 重启服务刷新文档

## 更多信息

- **Knife4j官方文档**: https://doc.xiaominfo.com/
- **SpringDoc官方文档**: https://springdoc.org/
- **OpenAPI规范**: https://swagger.io/specification/
