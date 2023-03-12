package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.service.RecommendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/recommend")
public class RecommendController {
    @Resource
    RecommendService recommendService;
    @GetMapping("/detailPageRecommend")
    public ResponseResult detailPageRecommend(Long userId, Long articleId){
        return recommendService.detailPageRecommend(userId,articleId);
    }
    @GetMapping("/interestRecommend")
    public ResponseResult interestRecommend(Long userId, Long articleId){
        return recommendService.interestRecommend(userId, articleId);
    }
    @GetMapping("/followRecommend")
    public ResponseResult followRecommend(Long userId){
        return recommendService.bigDataRecommend(userId);
    }
}
