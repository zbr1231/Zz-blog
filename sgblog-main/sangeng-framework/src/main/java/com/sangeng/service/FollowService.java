package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddFollowDto;

public interface FollowService {
    /**
     * 查询关注状态
     * @param userId 当前用户id
     * @param followId 当前用户是/否关注用户的id
     * @description 判断userId用户是否关注followId
     * @return status
     */
    ResponseResult followStatus(Long userId, Long followId);

    /**
     * 关注/取消关注
     * @return
     */
    ResponseResult followAction(AddFollowDto addFollowDto);

    /**
     * 获取用户的关注列表
     * @param userId
     * @return
     */
    ResponseResult getFollowList(Long userId);

    /**
     * 获取关注数量
     * @param userId
     * @return
     */
    int getFollowerCount(Long userId);
}
