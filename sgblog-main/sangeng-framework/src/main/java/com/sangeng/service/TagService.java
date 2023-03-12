package com.sangeng.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddInterestDto;
import com.sangeng.domain.dto.TagListDto;
import com.sangeng.domain.entity.ArticleTag;
import com.sangeng.domain.entity.Tag;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.TagVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2022-07-19 22:33:38
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    List<TagVo> listAllTag();

    /**
     * 查询用户已经添加的标签
     * @param userId
     * @return
     */
    ResponseResult getInterestTags(Long userId);

    /**
     * 根据用户id查询所有兴趣标签，包括标签是否被用户添加(status)
     * @param userId
     * @return
     */
    ResponseResult listAllInterestTags(Long userId);

    ResponseResult listByCategoryId(Long categoryId);

    ResponseResult getByArticleId(Long articleId);

    /**
     * 兴趣标签的行为
     * @param addInterestDto
     * @return
     */
    ResponseResult interestAction(AddInterestDto addInterestDto);
}

