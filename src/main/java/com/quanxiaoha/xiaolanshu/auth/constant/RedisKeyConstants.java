package com.quanxiaoha.xiaolanshu.auth.constant;

/**
 * @author: chisa
 * @version: v1.0.0
 * @description: 认证服务 Redis Key 常量
 **/
public class RedisKeyConstants {

    public static final String XIAOLANSHU_ID_GENERATOR_KEY = "xiaolanshu.id.generator";
    public static final String xiaolanshu_ID_GENERATOR_KEY = XIAOLANSHU_ID_GENERATOR_KEY;
    private static final String USER_ROLES_KEY_PREFIX = "user:roles:";
    private static final String ROLE_PERMISSIONS_KEY_PREFIX = "role:permissions:";

    /**
     * 验证码 KEY 前缀
     */
    private static final String VERIFICATION_CODE_KEY_PREFIX = "verification_code:";

    /**
     * 构建验证码 KEY
     * @param phone
     * @return
     */
    public static String buildVerificationCodeKey(String phone) {
        return VERIFICATION_CODE_KEY_PREFIX + phone;
    }

    public static String buildUserRoleKey(Long userId) {
        return USER_ROLES_KEY_PREFIX + userId;
    }

    public static String buildUserRoleKey(String phone) {
        return USER_ROLES_KEY_PREFIX + phone;
    }

    public static String buildRolePermissionsKey(Long roleId) {
        return ROLE_PERMISSIONS_KEY_PREFIX + roleId;
    }

    public static String buildRolePermissionsKey(String roleKey) {
        return ROLE_PERMISSIONS_KEY_PREFIX + roleKey;
    }
}

