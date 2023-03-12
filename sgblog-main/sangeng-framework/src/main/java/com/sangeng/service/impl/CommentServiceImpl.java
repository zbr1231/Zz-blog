package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Article;
import com.sangeng.domain.entity.Comment;
import com.sangeng.domain.entity.User;
import com.sangeng.domain.vo.CommentVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.exception.SystemException;
import com.sangeng.mapper.ArticleMapper;
import com.sangeng.mapper.CommentMapper;
import com.sangeng.service.ArticleService;
import com.sangeng.service.CommentService;
import com.sangeng.service.UserService;
import com.sangeng.utils.AuditUtil;
import com.sangeng.utils.BeanCopyUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-02-08 23:49:35
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CommentMapper commentMapper;

    @Autowired
    private AuditUtil audit;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //根评论 rootId为-1
        queryWrapper.eq(Comment::getRootId,-1);

        //评论类型
        queryWrapper.eq(Comment::getType,commentType);

        //分页查询
        Page<Comment> page = new Page(pageNum,pageSize);
        page.setSearchCount(false);
        page(page,queryWrapper);
        Long total = commentMapper.count(articleId);
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        commentVoList.stream().map(commentVo -> {
            Long userId = commentVo.getCreateBy();
            User user = userService.getById(userId);
            commentVo.setAvatar(user.getAvatar());
            return commentVo;
        }).collect(Collectors.toList());
        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList,total));
    }

    @Override
    public ResponseResult addComment(Long articleId, Comment comment) throws JSONException {
        //如果是文章类型评论则查询评论状态
        if("0".equals(comment.getType())){
            String isComment = articleMapper.getIsComment(articleId);
            if("0".equals(isComment)){
                return ResponseResult.errorResult(400,"文章已经禁用评论");
            }
        }

        //文本审核
        Map<String, String> map = audit.auditText(comment.getContent());
        if("0".equals(map.get("pass"))){
            //审核不通过返回信息
            return ResponseResult.errorResult(400,map.get("msg"));
        }
        save(comment);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delComment(Long commentId) {
        removeById(commentId);
        return ResponseResult.okResult();
    }

    /**
     * 查询未读的评论
     * @param userId
     * @return
     */
    @Override
    public ResponseResult getUnReadComment(Long userId) {
        List<Comment> unReadCommentList = commentMapper.getUnReadCommentList(userId);
        List<CommentVo> commentVoList = toCommentVoList(unReadCommentList);
        commentVoList.stream().map(commentVo -> {
            Long uId = commentVo.getCreateBy();
            User user = userService.getById(uId);
            Long articleId = commentVo.getArticleId();
            Article article = articleMapper.selectById(articleId);
            commentVo.setAvatar(user.getAvatar());
            commentVo.setTitle(article.getTitle());
            return commentVo;
        }).collect(Collectors.toList());
        //如果新评论少于5条，则加载最近的5条评论
        if(commentVoList.size()<5){
            commentVoList = getRecentComment(userId);
        }
        //将评论置为已读
        updateUserCommentMessageRead(userId);
        return ResponseResult.okResult(commentVoList);
    }

    @Override
    public List<CommentVo> getRecentComment(Long userId) {
        List<Comment> recentUnReadCommentList = commentMapper.getRecentUnReadCommentList(userId);
        List<CommentVo> commentVoList = toCommentVoList(recentUnReadCommentList);
        commentVoList.stream().map(commentVo -> {
            Long uId = commentVo.getCreateBy();
            User user = userService.getById(uId);
            Long articleId = commentVo.getArticleId();
            Article article = articleMapper.selectById(articleId);
            commentVo.setAvatar(user.getAvatar());
            commentVo.setTitle(article.getTitle());
            return commentVo;
        }).collect(Collectors.toList());
        return commentVoList;
    }

    /**
     * 查询未读的评论数量
     * @param userId
     * @return
     */
    @Override
    public ResponseResult getUnReadCount(Long userId) {
        Integer count = commentMapper.getUnReadCommentCount(userId);
        return ResponseResult.okResult(count);
    }

    @Override
    public Integer updateUserCommentMessageRead(Long userId) {
        return commentMapper.updateUserCommentMessageRead(userId);
    }

    @Override
    public ResponseResult updateRead(Long commentId) {
        commentMapper.updateRead(commentId);
        return ResponseResult.okResult();
    }


    /**
     * 根据根评论的id查询所对应的子评论的集合
     * @param id 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);
        List<CommentVo> commentVos = toCommentVoList(comments);
        commentVos.stream().map(commentVo -> {
            Long userId = commentVo.getCreateBy();
            User user = userService.getById(userId);
            commentVo.setAvatar(user.getAvatar());
            return commentVo;
        }).collect(Collectors.toList());
        return commentVos;
    }

    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历vo集合
        for (CommentVo commentVo : commentVos) {
            //通过creatyBy查询用户的昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if(commentVo.getToCommentUserId()!=-1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }
}

