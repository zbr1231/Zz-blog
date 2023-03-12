package com.sangeng.service.impl;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.ActionLog;
import com.sangeng.mapper.ActionLogMapper;
import com.sangeng.service.ActionLogService;
import com.sangeng.utils.RedisCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ActionLogServiceImpl implements ActionLogService {
    @Resource
    ActionLogMapper actionLogMapper;
    @Resource
    RedisCache redisCache;


    @Override
    public ResponseResult addLog(ActionLog actionLog) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //最近浏览(articleId, createTime)
        if("browse".equals(actionLog.getAction())){
            String articleId = actionLog.getArticleId().toString();
            String time = sdf.format(new Date());
            List<String> ids = redisCache.getCacheList("recentArticleId:" + actionLog.getUserId());
            List<String> times = redisCache.getCacheList("recentArticleTime:" + actionLog.getUserId());
            int len = ids.size();
            if(!ids.contains(articleId)) {
                //长度不到10添加
                if (ids.size() < 10) {
                    redisCache.listLeftPush("recentArticleId:" + actionLog.getUserId(),articleId);
                    redisCache.listLeftPush("recentArticleTime:" + actionLog.getUserId(),time);
                } else {
                    //超过10，删除再添加
                    redisCache.delCacheListValue("recentArticleId:" + actionLog.getUserId(),len);
                    redisCache.delCacheListValue("recentArticleTime:" + actionLog.getUserId(),len);
                    redisCache.listLeftPush("recentArticleId:" + actionLog.getUserId(),articleId);
                    redisCache.listLeftPush("recentArticleTime:" + actionLog.getUserId(),time);
                }
            }else{
                int i = ids.lastIndexOf(articleId);
                redisCache.delCacheListValue("recentArticleId:" + actionLog.getUserId(),i);
                redisCache.delCacheListValue("recentArticleTime:" + actionLog.getUserId(),i);
                redisCache.listLeftPush("recentArticleId:" + actionLog.getUserId(),articleId);
                redisCache.listLeftPush("recentArticleTime:" + actionLog.getUserId(),time);
            }
        }
        //添加行为日志
        int res = actionLogMapper.insert(actionLog);
        return ResponseResult.okResult();
        }
}

