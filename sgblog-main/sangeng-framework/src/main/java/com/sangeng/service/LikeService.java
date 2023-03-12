package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddLikeDto;

import java.util.List;

public interface LikeService {
    /**
     * 保存/删除点赞实体
     * @param likes
     * @return
     */
    ResponseResult saveLikes(List<AddLikeDto> likes);

    /**
     * 查询用户文章点赞状态
     * @param userId
     * @param articleId
     * @return
     */
    ResponseResult status(Long userId, Long articleId);
}
