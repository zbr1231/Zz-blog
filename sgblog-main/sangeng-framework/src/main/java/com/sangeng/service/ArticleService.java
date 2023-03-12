package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddArticleDto;
import com.sangeng.domain.dto.AddLikeDto;
import com.sangeng.domain.dto.ArticleDto;
import com.sangeng.domain.entity.Article;
import com.sangeng.domain.vo.ArticleOverviewVo;
import com.sangeng.domain.vo.ArticleVo;
import com.sangeng.domain.vo.PageVo;
import org.json.JSONException;

import java.util.List;
import java.util.Map;

public interface ArticleService extends IService<Article> {
    /**
     * 热门文章列表
     * @return
     */
    ResponseResult hotArticleList();

    /**
     * 文章列表
     * @param pageNum 页数
     * @param pageSize 页大小
     * @param categoryId 分类id
     * @return
     */
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    /**
     * 根据tagId查询文章
     * @param pageNum
     * @param pageSize
     * @param tagId
     * @return
     */
    ResponseResult articleListByTag(Integer pageNum, Integer pageSize, Long tagId);

    ResponseResult changeArticleTop(Long articleId, String isTop);

    ResponseResult articleListByUserId(Integer pageNum, Integer pageSize, Long userId);

    Long getCountByCategory(Long categoryId);

    ResponseResult getUserDrafts(Long userId);

    ResponseResult getRecentBrowse(Long userId);

    ResponseResult getArticleDetail(Long id);

    /**
     * 获取回收站文章
     * @param userId
     * @return
     */
    ResponseResult getRecycling(Long userId);

    /**
     * 获取文章的概述
     * @param id
     * @return
     */
    ArticleOverviewVo getArticleOverview(Long id);

    int getCountByUserId(Long userId);

    Long getUserTotalViewCount(Long userId);

    ResponseResult updateViewCount(Long userId,Long articleId);

    ResponseResult updateLikeCount(AddLikeDto addLikeDto);

    ResponseResult add(AddArticleDto article) throws JSONException;


    PageVo selectArticlePage(Article article, Integer pageNum, Integer pageSize);

    ArticleVo getInfo(Long id);

    void edit(ArticleDto article);


    boolean saveViewCountChange(List<Article> articles);

    boolean saveLikeCountChange(List<Article> articles);

    ResponseResult logicDelete(Long articleId);

    ResponseResult delete(Long articleId);


}
