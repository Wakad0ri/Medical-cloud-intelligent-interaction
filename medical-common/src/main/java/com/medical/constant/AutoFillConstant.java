package com.medical.constant;

/**
 * 公共字段自动填充相关常量
 * （AOP相关）
 */

public class AutoFillConstant {

    /**
     * 为了AOP统一处理
     * 定义一个常量，表示：获取当前名字的方法 并封装成Method对象
     * eg：Method setUpdateTime = User.class.getMethod("setUpdateTime", LocalDateTime.class);
     * 就可以把"setUpdateTime"这个常量字符串封装成固定常数
     */
    public static final String SET_CREATE_TIME = "setCreateTime";
    public static final String SET_UPDATE_TIME = "setUpdateTime";
    public static final String SET_CREATE_USER = "setCreateUser";
    public static final String SET_UPDATE_USER = "setUpdateUser";
}
