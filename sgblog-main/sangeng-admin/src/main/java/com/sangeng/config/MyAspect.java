package com.sangeng.config;

import com.sangeng.domain.entity.UserHolder;
import com.sangeng.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

@Aspect
@Slf4j
public class MyAspect {
    @Autowired
    private RedisCache redisCache;
    @Pointcut("execution(* com.sangeng.controller.*.*(..))")
    public void clazzPointcut(){

    }
    @Before("clazzPointcut()")
    public void before(JoinPoint joinPoint){
        log.warn("method before");
        String userId = UserHolder.getUserId();
        redisCache.expire("login:"+UserHolder.getUserId(),15, TimeUnit.MINUTES);
    }

}
