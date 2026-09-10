package com.quanxiaoha.framework.biz.context.holder;

/** Thread-local login user context shared by controllers and RPC clients. */
public final class LoginUserContextHolder {
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();

    private LoginUserContextHolder() {
    }

    public static void setUserId(Long userId) {
        USER_ID.set(userId);
    }

    public static Long getUserId() {
        return USER_ID.get();
    }

    public static void remove() {
        USER_ID.remove();
    }
}
