package com.sangeng.utils;

import com.sangeng.domain.entity.User;

public class UserLocalUtils {
    private static ThreadLocal<User> local = new ThreadLocal<>();
    public static void setLocal(User user){
        local.set(user);
    }
    public static User getLocal(){
        return local.get();
    }
}
