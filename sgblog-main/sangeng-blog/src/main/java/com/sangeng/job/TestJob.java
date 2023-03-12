package com.sangeng.job;


import com.sangeng.utils.RedisCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TestJob {
    @Resource
    RedisCache redisCache;
    //每天六点执行一次
    @Scheduled(cron = "0 0 6 * * ?")
    public void testJob(){
        //要执行的代码
        redisCache.deleteObject("repeat:viewCount");
    }
}
