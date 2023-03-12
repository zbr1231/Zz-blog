package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-08 23:49:33
 */
public interface CommentMapper extends BaseMapper<Comment> {
    @Select("select count(1) from sg_comment where article_id = #{articleId}")
    Long count(@Param("articleId") Long articleId);
    List<Comment> getUnReadCommentList(@Param("userId") Long userId);
    List<Comment> getRecentUnReadCommentList(@Param("userId") Long userId);
    Integer getUnReadCommentCount(@Param("userId") Long userId);

    /**
     * 用户查看完评论消息后，将所有新评论置为已读
     * @return
     */
    Integer updateUserCommentMessageRead(@Param("userId") Long userId);
    Integer updateRead(@Param("commentId") Long commentId);
}

