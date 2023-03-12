package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddLikeDto;
import com.sangeng.domain.entity.Article;
import com.sangeng.service.ArticleService;
import com.sangeng.service.LikeService;
import com.sangeng.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisController {
    @Resource
    RedisCache redisCache;
    @Resource
    ArticleService articleService;
    @Resource
    LikeService likeService;
    @PostMapping("/saveViewCount")
    public ResponseResult saveViewCount (){
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCountChange");
        if(viewCountMap.isEmpty()){
            log.warn("浏览量没有更新");
            return ResponseResult.okResult("浏览量没有更新");
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
        return ResponseResult.okResult("入库成功");
    }
    @PostMapping("/saveLikeCount")
    public ResponseResult saveLikeCount (){
        log.warn("获取点赞数====");
        //获取redis中的点赞数
        Map<String, Integer> likeCountMap = redisCache.getCacheMap("article:likeCountChange");
        Map<String, Integer> likeCountUserMap = redisCache.getCacheMap("article:likeCountUserChange");
        if(likeCountMap.isEmpty()){
            return ResponseResult.okResult("点赞数没有更新");
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
        return ResponseResult.okResult("入库成功");
    }
}
