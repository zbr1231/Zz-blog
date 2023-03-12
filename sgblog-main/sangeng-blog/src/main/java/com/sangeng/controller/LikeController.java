package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddLikeDto;
import com.sangeng.service.LikeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Resource
    LikeService likeService;
    @GetMapping("/status")
    public ResponseResult status(Long userId, Long articleId){
        return likeService.status(userId,articleId);
    }
}
