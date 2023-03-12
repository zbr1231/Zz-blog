package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Article;
import com.sangeng.domain.entity.ArticleTag;
import com.sangeng.mapper.ArticleTagMapper;
import com.sangeng.service.ArticleTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
    @Resource
    private ArticleTagMapper articleTagMapper;
    @Override
    public Map<Object,Object> listByTag(Integer pageNum, Integer pageSize, Long tagId) {
        Map map = new HashMap();
        QueryWrapper<ArticleTag> wrapper = new QueryWrapper<ArticleTag>();
        wrapper.eq("tag_id",tagId);
        Page<ArticleTag> page = new Page<>(pageNum,pageSize);
        page.setSearchCount(false);
        page(page,wrapper);
        Long total = articleTagMapper.count();
        List<ArticleTag> records = page.getRecords();
        map.put("records",records);
        map.put("total",total);
        return map;
    }

    @Override
    public boolean saveArticleTag(ArticleTag articleTag) {
        return save(articleTag);
    }

    @Override
    public int deleteByArticleId(Long articleId) {
        QueryWrapper<ArticleTag> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id",articleId);
        return articleTagMapper.delete(wrapper);
    }
}
