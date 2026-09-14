package com.quanxiaoha.xiaolanshu.user.biz.util;

/**
 * 用户参数校验工具。
 */
public final class ParamUtils {
    private ParamUtils() {
    }

    public static boolean checkNickname(String nickname) {
        return com.quanxiaoha.framework.common.util.ParamUtils.checkNickname(nickname);
    }

    public static boolean checkXiaolanshuId(String xiaolanshuId) {
        return com.quanxiaoha.framework.common.util.ParamUtils.checkxiaolanshuId(xiaolanshuId);
    }

    /** @deprecated use checkXiaolanshuId */
    @Deprecated
    public static boolean checkXiaohashuId(String xiaolanshuId) {
        return checkXiaolanshuId(xiaolanshuId);
    }

    public static boolean checkLength(String value, int length) {
        return com.quanxiaoha.framework.common.util.ParamUtils.checkLength(value, length);
    }
}
