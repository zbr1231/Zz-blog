package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.*;
import com.sangeng.domain.vo.ArticleOverviewVo;
import com.sangeng.domain.vo.HotArticleVo;
import com.sangeng.mapper.ArticleTagMapper;
import com.sangeng.mapper.RecommendMapper;
import com.sangeng.mapper.UserTagMapper;
import com.sangeng.service.ArticleService;
import com.sangeng.service.RecommendService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendServiceImpl implements RecommendService {
    @Resource
    RecommendMapper recommendMapper;
    @Resource
    UserTagMapper userTagMapper;
    @Resource
    ArticleService articleService;
    @Resource
    ArticleTagMapper articleTagMapper;
    @Override
    public ResponseResult detailPageRecommend(Long userId,Long articleId) {
        //根据当前文章标签查询文章
        //查询标签
        QueryWrapper<ArticleTag> wrapper = new QueryWrapper<ArticleTag>();
        wrapper.eq("article_id",articleId);
        Set<ArticleOverviewVo> res = new HashSet<>();
        List<ArticleTag> articleTags = articleTagMapper.selectList(wrapper);
        for (ArticleTag articleTag : articleTags) {
            ArticleTag oneByTagId = articleTagMapper.getOneByTagId(articleTag.getTagId(),articleId);
            if(oneByTagId==null){
                continue;
            }
            Long curId = oneByTagId.getArticleId();
            ArticleOverviewVo articleOverview = articleService.getArticleOverview(curId);
            if(!curId.equals(articleId)){
                res.add(articleOverview);
            }
        }
        //如果为空则采用基本推荐
        if(res.isEmpty()){
            List<HotArticleVo> articleVos = baseRecommend(userId, articleId);
            return ResponseResult.okResult(articleVos);
        }
        return ResponseResult.okResult(res);
    }

    @Override
    public ResponseResult interestRecommend(Long userId,Long articleId) {
        QueryWrapper<Interest> wrapper = new QueryWrapper<Interest>();
        wrapper.eq("user_id", userId);
        //根据用户查询兴趣标签
        List<Interest> interests = userTagMapper.selectList(wrapper);
        //根据兴趣标签查询文章id
        List<ArticleTag> list = new ArrayList<>();
        for (Interest interest : interests) {
            QueryWrapper<ArticleTag> wrapper1 = new QueryWrapper<ArticleTag>();
            wrapper1.eq("tag_id", interest.getTagId());
            //排除本文章id
            wrapper1.ne("article_id",articleId);
            List<ArticleTag> articleTags = articleTagMapper.selectList(wrapper1);
            if(articleTags.size() > 0) {
                list.add(articleTags.get(0));
            }
        }
        //如果之前查询结果为空则采用基本推荐
        if(list.size()==0){
            List<HotArticleVo> res = baseRecommend(userId, articleId);
            return ResponseResult.okResult(res);
        }
        List<ArticleOverviewVo> collect = list.stream().map(articleTag -> articleService.getArticleOverview(articleTag.getArticleId()))
                .collect(Collectors.toList());
        //去重
        Set<ArticleOverviewVo> set = new HashSet<>();
        set.addAll(collect);
        return ResponseResult.okResult(set);
    }

    @Override
    public List<HotArticleVo> baseRecommend(Long userId,Long articleId) {
        //根据浏览量降序查询出文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //排除当前文章
        queryWrapper.ne(Article::getId, articleId);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page(1,2);
        page.setSearchCount(false);
        articleService.page(page,queryWrapper);
        List<Article> articles = page.getRecords();
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return vs;
    }

    @Override
    public ResponseResult bigDataRecommend(Long userId) {
        List<Recommend> list = recommendMapper.getBigDataRecommend(userId);
        List<ArticleOverviewVo> res = list.stream().map(recommend -> articleService.getArticleOverview(recommend.getArticleId()))
                .collect(Collectors.toList());
        res.remove(null);
        return ResponseResult.okResult(res);
    }
    /**
     * 查询推荐管理
     *
     * @param id 推荐管理主键
     * @return 推荐管理
     */
    @Override
    public Recommend selectRecommendById(Long id)
    {
        return recommendMapper.selectRecommendById(id);
    }

    /**
     * 查询推荐管理列表
     *
     * @param recommend 推荐管理
     * @return 推荐管理
     */
    @Override
    public List<Recommend> selectRecommendList(Recommend recommend)
    {
        return recommendMapper.selectRecommendList(recommend);
    }

    /**
     * 新增推荐管理
     *
     * @param recommend 推荐管理
     * @return 结果
     */
    @Override
    public int insertRecommend(Recommend recommend)
    {
        return recommendMapper.insertRecommend(recommend);
    }

    /**
     * 修改推荐管理
     *
     * @param recommend 推荐管理
     * @return 结果
     */
    @Override
    public int updateRecommend(Recommend recommend)
    {
        return recommendMapper.updateRecommend(recommend);
    }

    /**
     * 批量删除推荐管理
     *
     * @param ids 需要删除的推荐管理主键
     * @return 结果
     */
    @Override
    public int deleteRecommendByIds(Long[] ids)
    {
        return recommendMapper.deleteRecommendByIds(ids);
    }

    /**
     * 删除推荐管理信息
     *
     * @param id 推荐管理主键
     * @return 结果
     */
    @Override
    public int deleteRecommendById(Long id)
    {
        return recommendMapper.deleteRecommendById(id);
    }
}
