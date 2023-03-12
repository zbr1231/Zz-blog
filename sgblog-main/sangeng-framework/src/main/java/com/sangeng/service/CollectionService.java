package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddCollectionDto;
import com.sangeng.domain.entity.Collection;

public interface CollectionService {
    /**
     * 查询用户收藏的文章
     * @param userId
     * @return
     */
    ResponseResult getCollectionArticles(Long userId);

    /**
     * 查询文章收藏状态
     * @param userId
     * @param articleId
     * @return
     */
    ResponseResult getCollectStatus(Long userId,Long articleId);

    /**
     * 文章收藏行为
     * @param addCollectionDto
     * @return
     */
    ResponseResult collectAction(AddCollectionDto addCollectionDto);
}
