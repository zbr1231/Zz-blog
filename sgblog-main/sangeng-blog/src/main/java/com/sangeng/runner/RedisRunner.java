package com.sangeng.runner;

import com.sangeng.domain.entity.Article;
import com.sangeng.mapper.ArticleMapper;
import com.sangeng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RedisRunner implements CommandLineRunner {

    @Resource
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息  id  viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
                    return article.getViewCount()==null?0:article.getViewCount().intValue();
                }));
        Map<String, Integer> likeCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
                    return article.getLikeCount()==null?0:article.getLikeCount().intValue();
                }));
        //存储到redis中
        redisCache.setCacheMap("article:viewCount",viewCountMap);
        redisCache.setCacheMap("article:likeCount",likeCountMap);
    }
}
