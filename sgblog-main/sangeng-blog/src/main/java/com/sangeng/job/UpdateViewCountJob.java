package com.sangeng.job;

import com.sangeng.domain.entity.Article;
import com.sangeng.service.ArticleService;
import com.sangeng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0 */1 * * ?")
    public void updateViewCount(){
        System.out.println("获取浏览量====");
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCountChange");
        if(viewCountMap.isEmpty()){
            System.out.println("浏览量没有更新");
            return;
        }
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> Article
                                .builder()
                                .id(Long.valueOf(entry.getKey()))
                                .viewCount(entry.getValue().longValue())
                                .build()
                        )
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.saveViewCountChange(articles);

    }
}
