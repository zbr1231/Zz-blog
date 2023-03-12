package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddFollowDto;
import com.sangeng.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/follow")
@Slf4j
public class FollowController {
    @Resource
    FollowService followService;
    @GetMapping("/status")
    public ResponseResult getFollowStatus(Long userId, Long followId) {
        log.warn("/follow/status {},{}",userId,followId);
        return followService.followStatus(userId,followId);
    }
    @PostMapping("/action")
    public ResponseResult followAction(@RequestBody AddFollowDto addFollowDto){
        log.warn("/follow/action {}",addFollowDto);
        return followService.followAction(addFollowDto);
    }
    @GetMapping("/followList")
    public ResponseResult getFollowList(Long userId){
        log.warn("/followList {}",userId);
        return followService.getFollowList(userId);
    }

}
