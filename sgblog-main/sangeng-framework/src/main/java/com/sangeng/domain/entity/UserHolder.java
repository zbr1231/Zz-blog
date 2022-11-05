package com.sangeng.domain.entity;

public class UserHolder {
    private static final ThreadLocal<String> TL = new ThreadLocal<>();

    public static void setUserId(String userId) {
        TL.set(userId);
    }

    public static String getUserId() {
        return TL.get();
    }

    public static void removeUserId() {
        TL.remove();
    }
}
