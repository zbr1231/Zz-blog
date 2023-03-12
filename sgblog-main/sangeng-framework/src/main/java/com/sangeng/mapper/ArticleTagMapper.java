package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.ArticleTag;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author zz
 */
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {
    @Select("select article_id, tag_id from sg_article_tag where tag_id=#{tagId} and article_id!=#{articleId} limit 0,1")
    ArticleTag getOneByTagId(@Param("tagId") Long tagId,@Param("articleId")Long articleId);
    @Select("select sum(1) from sg_article_tag where tag_id = #{tagId}")
    Long getArticleCount(@Param("tagId") Long tagId);
    @Select("select count(1) from sg_article_tag")
    Long count();
}