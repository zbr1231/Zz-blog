package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.ArticleSearchDto;
import com.sangeng.domain.entity.ArticleForEs;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.mapper.EsArticleMapper;
import com.sangeng.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/search")
public class EsController {
    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Resource
    EsArticleMapper esArticleMapper;
    @GetMapping("/article/text")
    public ResponseResult getByText(Integer pageNum,Integer pageSize,String text){
        log.warn("/search/article/text {},{},{}",pageNum,pageSize,text);
        return esArticleMapper.highLightText(pageNum, pageSize, text);
    }
    @GetMapping("/conditionArticle")
    public ResponseResult articleSearch(@RequestBody ArticleSearchDto articleSearchDto){
        return esArticleMapper.conditionHighLight(articleSearchDto);
    }
}
