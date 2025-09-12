/**
 * API统一导出文件
 * 根据后端Controller结构组织API接口
 */

// 管理员相关API (AdminController)
export * from './admin'

// 用户相关API (兼容旧版本)
export * from './user'

// 通用API (CommonController)
export * from './common'

// 医生相关API (DoctorController) - 重新实现
export * from './doctor'

// 科室相关API (DepartmentController) - 部分实现
export * from './department'

/**
 * API端点映射表 - 根据实际后端Controller重新整理
 *
 * 管理端接口 (/admin/*)
 * ├── AdminController (/admin) - 员工管理
 * │   ├── POST /admin/login - 管理员登录 (@RequestBody AdminLoginDTO)
 * │   ├── POST /admin/logout - 管理员登出
 * │   ├── POST /admin/add - 添加管理员 (@RequestBody AdminAddDTO)
 * │   ├── GET /admin/page - 分页查询管理员 (AdminPageQueryDTO)
 * │   ├── PUT /admin/status/{status} - 启用禁用管理员 (@RequestParam("ids"))
 * │   ├── PUT /admin/update - 修改管理员信息 (@RequestBody AdminUpdateDTO)
 * │   ├── PUT /admin/password - 修改管理员密码 (@RequestParam)
 * │   └── DELETE /admin/delete - 删除管理员 (@RequestParam("ids"))
 * │
 * ├── DepartmentController (/admin/department) - 科室管理 [已删除，待重新实现]
 * │
 * ├── DoctorController (/admin/doctor) - 医生管理 [已删除，待重新实现]
 * │
 * └── CommonController (/admin/common) - 通用接口
 *     └── POST /admin/common/upload - 文件上传 (@RequestParam("file"))
 */
