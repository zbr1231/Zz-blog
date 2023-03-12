package com.sangeng.job;

import com.sangeng.domain.dto.AddLikeDto;
import com.sangeng.domain.entity.Article;
import com.sangeng.service.ArticleService;
import com.sangeng.service.LikeService;
import com.sangeng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateLikeCountJob {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;
    @Resource
    private LikeService likeService;
    @Scheduled(cron = "0 0 */1 * * ?")
    public void updateViewCount(){
        System.out.println("获取点赞数====");
        //获取redis中的点赞数
        Map<String, Integer> likeCountMap = redisCache.getCacheMap("article:likeCountChange");
        Map<String, Integer> likeCountUserMap = redisCache.getCacheMap("article:likeCountUserChange");
        if(likeCountMap.isEmpty()){
            System.out.println("点赞数没有更新");
            return;
        }
        //更新点赞数
        List<Article> articles = likeCountMap.entrySet()
                .stream()
                .map(entry ->
                        Article
                                .builder()
                                .id(Long.valueOf(entry.getKey()))
                                .likeCount(entry.getValue().longValue())
                                .build())
                .collect(Collectors.toList());
        //添加用户点赞记录
        List<AddLikeDto> likes = likeCountUserMap.entrySet()
                .stream()
                .map(entry -> AddLikeDto
                        .builder()
                        .articleId(Long.valueOf(entry.getKey().split(":")[0]))
                        .userId(Long.valueOf(entry.getKey().split(":")[1]))
                        //-1删除/1添加/0不做操作
                        .status(entry.getValue().intValue())
                        .build())
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.saveLikeCountChange(articles);
        likeService.saveLikes(likes);

    }
}
