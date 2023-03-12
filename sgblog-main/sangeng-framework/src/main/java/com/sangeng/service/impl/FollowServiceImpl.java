package com.sangeng.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddFollowDto;
import com.sangeng.domain.entity.Follow;
import com.sangeng.domain.entity.User;
import com.sangeng.domain.vo.UserInfoVo;
import com.sangeng.mapper.FollowMapper;
import com.sangeng.mapper.UserMapper;
import com.sangeng.service.FollowService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowServiceImpl implements FollowService {
    @Resource
    FollowMapper followMapper;
    @Resource
    UserMapper userMapper;
    @Override
    public ResponseResult followStatus(Long userId, Long followId) {
        QueryWrapper<Follow> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("follow_id",followId);
        Follow follow = followMapper.selectOne(wrapper);
        int status = 0;
        if(follow !=null){
            status = 1;
        }
        return ResponseResult.okResult(status);
    }

    @Override
    public ResponseResult followAction(AddFollowDto addFollowDto) {
        Long userId = addFollowDto.getUserId();
        Long followId = addFollowDto.getFollowId();
        int status = addFollowDto.getStatus();
        //删除
        if(status==1){
            QueryWrapper<Follow> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",userId);
            wrapper.eq("follow_id",followId);
            followMapper.delete(wrapper);
        }else{
            followMapper.insert(new Follow(userId,followId));
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getFollowList(Long userId) {
        QueryWrapper<Follow> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<Follow> follows = followMapper.selectList(wrapper);
        List<User> collect = follows.stream().map(follow -> userMapper.selectById(follow.getFollowId())).collect(Collectors.toList());
        List<UserInfoVo> userInfoVos = BeanCopyUtils.copyBeanList(collect, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVos);
    }

    @Override
    public int getFollowerCount(Long userId) {
        QueryWrapper<Follow> wrapper = new QueryWrapper<>();
        wrapper.eq("follow_id", userId);
        return followMapper.selectCount(wrapper);
    }
}
