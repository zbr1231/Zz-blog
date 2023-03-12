package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.Article;
import com.sangeng.domain.vo.ArticleOverviewVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {
    void updateViewCountChange(List<Article> articles);
    void updatelikeCountChange(List<Article> articles);
    @Select("select is_comment from sg_article where id = #{articleId}")
    String getIsComment(@Param("articleId") Long articleId);
    @Select("select id,title,summary,thumbnail from sg_article where id = #{articleId}")
    ArticleOverviewVo getArticleOverview(@Param("articleId") Long articleId);
    @Select("SELECT sum(view_count) FROM sg_article where user_id=#{userId}")
    Long getUserTotalCount(@Param("userId") Long userId);
    @Select("select sum(1) from sg_article where category_id = #{categoryId} and status = 0 and del_flag = 0")
    Long getCountByCategory(@Param("categoryId") Long categoryId);
    @Select("select * from sg_article where user_id = #{userId} and del_flag = 1")
    List<Article> getRecyclings(@Param("userId") Long userId);
    @Update("update sg_article set del_flag=1 where id=#{articleId}")
    boolean logicDelete(@Param("articleId") Long articleId);
    @Delete("delete from sg_article where id = #{articleId}")
    boolean delete(@Param("articleId") Long articleId);
    @Select("select count(1) from sg_article")
    Long count();
}
