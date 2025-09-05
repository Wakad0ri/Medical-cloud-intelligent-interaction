# 医云智能互动系统 - 前端接口文档

## 📋 目录
- [1. 统一响应格式](#1-统一响应格式)
- [2. 认证接口](#2-认证接口)
- [3. 管理员端接口](#3-管理员端接口)
- [4. 医生端接口](#4-医生端接口)
- [5. 患者端接口](#5-患者端接口)
- [6. 公共接口](#6-公共接口)

---

## 1. 统一响应格式

### 成功响应
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

### 错误响应
```json
{
  "code": 400,
  "message": "错误信息",
  "data": null
}
```

### 分页响应
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 100,
    "records": []
  }
}
```

---

## 2. 认证接口

### 2.1 用户登录
- **接口路径**: `POST /api/auth/login`
- **请求参数**: 
```json
{
  "username": "string",     // 用户名
  "password": "string",     // 密码
  "userType": "integer"     // 用户类型：1-患者，2-医生，3-管理员
}
```
- **响应数据**: 
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "token": "string",      // JWT令牌
    "userInfo": {
      "id": "long",         // 用户ID
      "username": "string", // 用户名
      "userType": "integer",// 用户类型
      "realName": "string", // 真实姓名
      "avatar": "string"    // 头像URL
    }
  }
}
```

### 2.2 退出登录
- **接口路径**: `POST /api/auth/logout`
- **请求头**: `Authorization: Bearer {token}`
- **响应数据**: `Result<String>`

---

## 3. 管理员端接口

### 3.1 用户管理

#### 获取用户列表
- **接口路径**: `GET /api/admin/users`
- **请求参数**: 
```json
{
  "page": "integer",        // 页码，默认1
  "pageSize": "integer",    // 每页大小，默认10
  "username": "string",     // 用户名（可选）
  "userType": "integer",    // 用户类型（可选）
  "status": "integer"       // 状态（可选）
}
```
- **响应数据**: `PageResult<User>`

#### 添加用户
- **接口路径**: `POST /api/admin/users`
- **请求参数**: 
```json
{
  "username": "string",     // 用户名
  "password": "string",     // 密码
  "realName": "string",     // 真实姓名
  "phone": "string",        // 手机号
  "email": "string",        // 邮箱（可选）
  "userType": "integer",    // 用户类型
  "status": "integer"       // 状态：0-禁用，1-启用
}
```
- **响应数据**: `Result<String>`

#### 更新用户
- **接口路径**: `PUT /api/admin/users/{id}`
- **请求参数**: 
```json
{
  "realName": "string",     // 真实姓名
  "phone": "string",        // 手机号
  "email": "string",        // 邮箱
  "status": "integer"       // 状态
}
```
- **响应数据**: `Result<String>`

#### 删除用户
- **接口路径**: `DELETE /api/admin/users/{id}`
- **响应数据**: `Result<String>`

#### 启用/禁用用户
- **接口路径**: `PUT /api/admin/users/{id}/status`
- **请求参数**: 
```json
{
  "status": "integer"       // 状态：0-禁用，1-启用
}
```
- **响应数据**: `Result<String>`

### 3.2 医生管理

#### 获取医生列表
- **接口路径**: `GET /api/admin/doctors`
- **请求参数**: 
```json
{
  "page": "integer",        // 页码
  "pageSize": "integer",    // 每页大小
  "name": "string",         // 医生姓名（可选）
  "departmentId": "long",   // 科室ID（可选）
  "title": "string",        // 职称（可选）
  "status": "integer"       // 状态（可选）
}
```
- **响应数据**: `PageResult<Doctor>`

#### 添加医生
- **接口路径**: `POST /api/admin/doctors`
- **请求参数**: 
```json
{
  "username": "string",     // 用户名
  "password": "string",     // 密码
  "name": "string",         // 医生姓名
  "phone": "string",        // 手机号
  "email": "string",        // 邮箱
  "departmentId": "long",   // 科室ID
  "title": "string",        // 职称
  "specialty": "string",    // 专长
  "introduction": "string", // 简介
  "avatar": "string",       // 头像URL
  "status": "integer"       // 状态
}
```
- **响应数据**: `Result<String>`

#### 更新医生信息
- **接口路径**: `PUT /api/admin/doctors/{id}`
- **请求参数**: 同添加医生（除username和password）
- **响应数据**: `Result<String>`

#### 删除医生
- **接口路径**: `DELETE /api/admin/doctors/{id}`
- **响应数据**: `Result<String>`

### 3.3 科室管理

#### 获取科室列表
- **接口路径**: `GET /api/admin/departments`
- **响应数据**: `Result<List<Department>>`

#### 添加科室
- **接口路径**: `POST /api/admin/departments`
- **请求参数**: 
```json
{
  "name": "string",         // 科室名称
  "description": "string",  // 科室描述
  "location": "string",     // 科室位置
  "phone": "string",        // 科室电话
  "status": "integer"       // 状态
}
```
- **响应数据**: `Result<String>`

#### 更新科室
- **接口路径**: `PUT /api/admin/departments/{id}`
- **请求参数**: 同添加科室
- **响应数据**: `Result<String>`

#### 删除科室
- **接口路径**: `DELETE /api/admin/departments/{id}`
- **响应数据**: `Result<String>`

### 3.4 数据统计

#### 获取总览数据
- **接口路径**: `GET /api/admin/statistics/overview`
- **响应数据**: 
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "totalUsers": "integer",        // 总用户数
    "totalDoctors": "integer",      // 总医生数
    "totalPatients": "integer",     // 总患者数
    "totalAppointments": "integer", // 总预约数
    "todayAppointments": "integer", // 今日预约数
    "totalConsultations": "integer" // 总问诊数
  }
}
```

#### 获取用户统计
- **接口路径**: `GET /api/admin/statistics/users`
- **请求参数**: 
```json
{
  "startDate": "string",    // 开始日期 YYYY-MM-DD
  "endDate": "string",      // 结束日期 YYYY-MM-DD
  "type": "string"          // 统计类型：day/week/month
}
```
- **响应数据**: `Result<UserStatisticsVO>`

#### 获取预约统计
- **接口路径**: `GET /api/admin/statistics/appointments`
- **请求参数**: 同用户统计
- **响应数据**: `Result<AppointmentStatisticsVO>`

---

## 4. 医生端接口

### 4.1 预约管理

#### 获取我的预约
- **接口路径**: `GET /api/doctor/appointments`
- **请求参数**: 
```json
{
  "page": "integer",        // 页码
  "pageSize": "integer",    // 每页大小
  "date": "string",         // 预约日期（可选）
  "status": "integer"       // 预约状态（可选）
}
```
- **响应数据**: `PageResult<Appointment>`

#### 确认预约
- **接口路径**: `PUT /api/doctor/appointments/{id}/confirm`
- **响应数据**: `Result<String>`

#### 取消预约
- **接口路径**: `PUT /api/doctor/appointments/{id}/cancel`
- **请求参数**: 
```json
{
  "reason": "string"        // 取消原因
}
```
- **响应数据**: `Result<String>`

### 4.2 问诊管理

#### 获取问诊列表
- **接口路径**: `GET /api/doctor/consultations`
- **请求参数**: 
```json
{
  "page": "integer",        // 页码
  "pageSize": "integer",    // 每页大小
  "status": "integer",      // 问诊状态（可选）
  "patientName": "string"   // 患者姓名（可选）
}
```
- **响应数据**: `PageResult<Consultation>`

#### 开始问诊
- **接口路径**: `POST /api/doctor/consultations/{id}/start`
- **响应数据**: `Result<String>`

#### 结束问诊
- **接口路径**: `PUT /api/doctor/consultations/{id}/finish`
- **请求参数**: 
```json
{
  "diagnosis": "string",    // 诊断结果
  "prescription": "string", // 处方
  "advice": "string",       // 医嘱
  "followUpDate": "string"  // 复诊日期（可选）
}
```
- **响应数据**: `Result<String>`

#### 发送消息
- **接口路径**: `POST /api/doctor/consultations/{id}/messages`
- **请求参数**: 
```json
{
  "content": "string",      // 消息内容
  "messageType": "integer", // 消息类型：1-文本，2-图片，3-语音
  "attachmentUrl": "string" // 附件URL（可选）
}
```
- **响应数据**: `Result<String>`

### 4.3 患者管理

#### 获取我的患者
- **接口路径**: `GET /api/doctor/patients`
- **请求参数**: 
```json
{
  "page": "integer",        // 页码
  "pageSize": "integer",    // 每页大小
  "name": "string",         // 患者姓名（可选）
  "phone": "string"         // 手机号（可选）
}
```
- **响应数据**: `PageResult<Patient>`

#### 查看患者详情
- **接口路径**: `GET /api/doctor/patients/{id}`
- **响应数据**: `Result<PatientDetailVO>`

#### 更新患者信息
- **接口路径**: `PUT /api/doctor/patients/{id}`
- **请求参数**: 
```json
{
  "notes": "string",        // 医生备注
  "tags": "string"          // 患者标签
}
```
- **响应数据**: `Result<String>`

### 4.4 排班管理

#### 获取我的排班
- **接口路径**: `GET /api/doctor/schedules`
- **请求参数**: 
```json
{
  "startDate": "string",    // 开始日期
  "endDate": "string"       // 结束日期
}
```
- **响应数据**: `Result<List<Schedule>>`

#### 设置排班
- **接口路径**: `POST /api/doctor/schedules`
- **请求参数**: 
```json
{
  "date": "string",         // 排班日期
  "timeSlots": [            // 时间段列表
    {
      "startTime": "string", // 开始时间 HH:mm
      "endTime": "string",   // 结束时间 HH:mm
      "maxPatients": "integer" // 最大患者数
    }
  ]
}
```
- **响应数据**: `Result<String>`

#### 更新排班
- **接口路径**: `PUT /api/doctor/schedules/{id}`
- **请求参数**: 同设置排班
- **响应数据**: `Result<String>`

---

## 5. 患者端接口

### 5.1 预约挂号

#### 获取科室列表
- **接口路径**: `GET /api/patient/departments`
- **响应数据**: `Result<List<Department>>`

#### 获取医生列表
- **接口路径**: `GET /api/patient/doctors`
- **请求参数**: 
```json
{
  "departmentId": "long",   // 科室ID（可选）
  "name": "string",         // 医生姓名（可选）
  "title": "string"         // 职称（可选）
}
```
- **响应数据**: `Result<List<Doctor>>`

#### 获取医生排班
- **接口路径**: `GET /api/patient/doctors/{id}/schedules`
- **请求参数**: 
```json
{
  "date": "string"          // 查询日期 YYYY-MM-DD
}
```
- **响应数据**: `Result<List<Schedule>>`

#### 预约挂号
- **接口路径**: `POST /api/patient/appointments`
- **请求参数**: 
```json
{
  "doctorId": "long",       // 医生ID
  "scheduleId": "long",     // 排班ID
  "appointmentDate": "string", // 预约日期
  "timeSlot": "string",     // 时间段
  "symptoms": "string",     // 症状描述
  "contactPhone": "string"  // 联系电话
}
```
- **响应数据**: `Result<String>`

#### 取消预约
- **接口路径**: `PUT /api/patient/appointments/{id}/cancel`
- **响应数据**: `Result<String>`

#### 获取我的预约
- **接口路径**: `GET /api/patient/appointments`
- **请求参数**: 
```json
{
  "page": "integer",        // 页码
  "pageSize": "integer",    // 每页大小
  "status": "integer"       // 预约状态（可选）
}
```
- **响应数据**: `PageResult<Appointment>`

### 5.2 在线问诊

#### 发起问诊
- **接口路径**: `POST /api/patient/consultations`
- **请求参数**: 
```json
{
  "doctorId": "long",       // 医生ID（可选，系统自动分配）
  "title": "string",        // 问诊标题
  "symptoms": "string",     // 症状描述
  "duration": "string",     // 症状持续时间
  "images": ["string"]      // 症状图片URL列表（可选）
}
```
- **响应数据**: `Result<String>`

#### 获取问诊记录
- **接口路径**: `GET /api/patient/consultations`
- **请求参数**: 
```json
{
  "page": "integer",        // 页码
  "pageSize": "integer",    // 每页大小
  "status": "integer"       // 问诊状态（可选）
}
```
- **响应数据**: `PageResult<Consultation>`

#### 发送消息
- **接口路径**: `POST /api/patient/consultations/{id}/messages`
- **请求参数**: 
```json
{
  "content": "string",      // 消息内容
  "messageType": "integer", // 消息类型：1-文本，2-图片，3-语音
  "attachmentUrl": "string" // 附件URL（可选）
}
```
- **响应数据**: `Result<String>`

#### 获取消息列表
- **接口路径**: `GET /api/patient/consultations/{id}/messages`
- **响应数据**: `Result<List<Message>>`

### 5.3 健康档案

#### 获取健康档案
- **接口路径**: `GET /api/patient/health-records`
- **响应数据**: `Result<HealthRecordVO>`

#### 更新健康档案
- **接口路径**: `PUT /api/patient/health-records`
- **请求参数**: 
```json
{
  "height": "double",       // 身高(cm)
  "weight": "double",       // 体重(kg)
  "bloodType": "string",    // 血型
  "allergies": "string",    // 过敏史
  "medicalHistory": "string", // 病史
  "familyHistory": "string", // 家族史
  "emergencyContact": "string", // 紧急联系人
  "emergencyPhone": "string"    // 紧急联系电话
}
```
- **响应数据**: `Result<String>`

#### 上传检查报告
- **接口路径**: `POST /api/patient/health-records/reports`
- **请求参数**: 
```json
{
  "title": "string",        // 报告标题
  "reportType": "string",   // 报告类型
  "reportDate": "string",   // 报告日期
  "hospitalName": "string", // 医院名称
  "doctorName": "string",   // 医生姓名
  "reportUrl": "string",    // 报告文件URL
  "description": "string"   // 报告描述
}
```
- **响应数据**: `Result<String>`

### 5.4 个人中心

#### 获取个人信息
- **接口路径**: `GET /api/patient/profile`
- **响应数据**: `Result<PatientProfileVO>`

#### 更新个人信息
- **接口路径**: `PUT /api/patient/profile`
- **请求参数**: 
```json
{
  "realName": "string",     // 真实姓名
  "phone": "string",        // 手机号
  "email": "string",        // 邮箱
  "gender": "integer",      // 性别：0-女，1-男
  "birthday": "string",     // 生日
  "idCard": "string",       // 身份证号
  "address": "string",      // 地址
  "avatar": "string"        // 头像URL
}
```
- **响应数据**: `Result<String>`

#### 修改密码
- **接口路径**: `PUT /api/patient/password`
- **请求参数**: 
```json
{
  "oldPassword": "string",  // 原密码
  "newPassword": "string"   // 新密码
}
```
- **响应数据**: `Result<String>`

---

## 6. 公共接口

### 6.1 文件上传
- **接口路径**: `POST /api/common/upload`
- **请求参数**: `multipart/form-data`
- **响应数据**: 
```json
{
  "code": 200,
  "message": "上传成功",
  "data": {
    "url": "string",          // 文件访问URL
    "fileName": "string",     // 文件名
    "fileSize": "long"        // 文件大小
  }
}
```

### 6.2 AI智能问答
- **接口路径**: `POST /api/ai/chat`
- **请求参数**: 
```json
{
  "question": "string",     // 用户问题
  "context": "string"       // 上下文（可选）
}
```
- **响应数据**: 
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "answer": "string",     // AI回答
    "confidence": "double", // 置信度
    "suggestions": ["string"] // 建议问题列表
  }
}
```

### 6.3 获取健康建议
- **接口路径**: `POST /api/ai/health-advice`
- **请求参数**: 
```json
{
  "symptoms": "string",     // 症状描述
  "age": "integer",         // 年龄
  "gender": "integer",      // 性别
  "medicalHistory": "string" // 病史（可选）
}
```
- **响应数据**: 
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "advice": "string",     // 健康建议
    "severity": "string",   // 严重程度
    "recommendations": ["string"], // 推荐措施
    "needsAttention": "boolean"    // 是否需要就医
  }
}
```

---

## 📝 注意事项

1. **认证方式**: 除登录接口外，所有接口都需要在请求头中携带JWT令牌
   ```
   Authorization: Bearer {token}
   ```

2. **错误码说明**:
   - `200`: 操作成功
   - `400`: 请求参数错误
   - `401`: 未授权访问
   - `403`: 权限不足
   - `404`: 资源不存在
   - `500`: 服务器内部错误

3. **日期格式**: 统一使用 `YYYY-MM-DD` 格式
4. **时间格式**: 统一使用 `HH:mm` 格式
5. **分页参数**: 页码从1开始，默认每页10条记录

---

## 🔄 更新日志

- **v1.0.0** (2025-09-04): 初始版本，包含所有基础接口
