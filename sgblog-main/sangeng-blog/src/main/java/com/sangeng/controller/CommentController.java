package com.sangeng.controller;

import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddCommentDto;
import com.sangeng.domain.entity.Comment;
import com.sangeng.service.CommentService;
import com.sangeng.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/comment")
@Validated
@Api(tags = "评论",description = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(@NotNull(message = "文章id不能为空") Long articleId,
                                      @NotNull(message = "页数不能为空") Integer pageNum,
                                      @NotNull(message = "页大小不能为空") Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }
    @GetMapping("/unreadCommentList")
    public ResponseResult unreadCommentList(@NotNull(message = "用户id不能为空") Long userId){
        return commentService.getUnReadComment(userId);
    }
    @GetMapping("/unreadCommentCount")
    public ResponseResult unreadCommentCount(@NotNull(message = "用户id不能为空") Long userId){
        return commentService.getUnReadCount(userId);
    }
    @PostMapping("/updateRead")
    public ResponseResult updateRead(@NotNull(message = "评论id不能为空") Long commentId){
        return commentService.updateRead(commentId);
    }

    @PostMapping
    public ResponseResult addComment(@Valid @RequestBody AddCommentDto addCommentDto) throws JSONException {
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(addCommentDto.getArticleId(),comment);
    }
    @DeleteMapping
    public ResponseResult deleteComment(@NotNull(message = "评论id不能为空") Long commentId){
        return commentService.delComment(commentId);
    }

    @GetMapping("/linkCommentList")
    @ApiOperation(value = "友链评论列表",notes = "获取一页友链评论")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "pageNum",value = "页号"),
           @ApiImplicitParam(name = "pageSize",value = "每页大小")
    }
    )
    public ResponseResult linkCommentList(@NotNull(message = "页数不能为空") Integer pageNum,
                                          @NotNull(message = "页大小不能空") Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}
