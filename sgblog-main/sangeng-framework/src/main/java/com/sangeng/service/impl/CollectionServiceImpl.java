package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddCollectionDto;
import com.sangeng.domain.entity.Collection;
import com.sangeng.domain.vo.ArticleOverviewVo;
import com.sangeng.mapper.CollectionMapper;
import com.sangeng.service.ArticleService;
import com.sangeng.service.CollectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Resource
    CollectionMapper collectionMapper;
    @Resource
    ArticleService articleService;
    @Override
    public ResponseResult getCollectionArticles(Long userId) {
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<Collection> list = collectionMapper.selectList(wrapper);
        List<ArticleOverviewVo> collect = list.stream()
                .map(collection -> articleService.getArticleOverview(collection.getArticleId()))
                .collect(Collectors.toList());

        return ResponseResult.okResult(collect);
    }

    @Override
    public ResponseResult getCollectStatus(Long userId, Long articleId) {
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("article_id", articleId);
        Collection collection = collectionMapper.selectOne(wrapper);
        int status = 0;
        if(collection!=null){
            status = 1;
        }
        return ResponseResult.okResult(status);
    }

    @Override
    public ResponseResult collectAction(AddCollectionDto addCollectionDto) {
        Long userId = addCollectionDto.getUserId();
        Long articleId = addCollectionDto.getArticleId();
        int status = addCollectionDto.getStatus();
        if(status==1){
            QueryWrapper<Collection> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",userId);
            wrapper.eq("article_id",articleId);
            collectionMapper.delete(wrapper);
        }else{
            collectionMapper.insert(new Collection(userId,articleId));
        }
        return ResponseResult.okResult();
    }
}
