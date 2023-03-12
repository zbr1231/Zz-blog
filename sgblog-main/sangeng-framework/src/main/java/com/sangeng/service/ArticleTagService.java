package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.ArticleTag;

import java.util.List;
import java.util.Map;

/**
 * @Author zz
 */
public interface ArticleTagService extends IService<ArticleTag> {
    Map<Object,Object> listByTag(Integer pageNum, Integer pageSize, Long tagId);
    boolean saveArticleTag(ArticleTag articleTag);
    int deleteByArticleId(Long articleId);
}
