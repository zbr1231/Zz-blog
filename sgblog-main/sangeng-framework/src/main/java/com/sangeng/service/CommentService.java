package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Comment;
import com.sangeng.domain.vo.CommentVo;
import org.json.JSONException;

import java.util.List;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-02-08 23:49:35
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Long articleId, Comment comment) throws JSONException;

    ResponseResult delComment(Long commentId);

    ResponseResult getUnReadComment(Long userId);

    List<CommentVo> getRecentComment(Long userId);

    ResponseResult getUnReadCount(Long userId);
    /**
     * 用户查看完评论消息后，将所有新评论置为已读
     * @return
     */
    Integer updateUserCommentMessageRead(Long userId);
    ResponseResult updateRead(Long commentId);
}

