package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {
    void updateViewCountChange(List<Article> articles);
    @Select("select is_comment from sg_article where id = #{articleId}")
    String getIsComment(@Param("articleId") Long articleId);
}
