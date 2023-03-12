package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddCollectionDto;
import com.sangeng.domain.entity.Collection;
import com.sangeng.service.CollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/collection")
@Slf4j
public class CollectionController {
    @Resource
    CollectionService collectionService;
    @GetMapping("/articleOverviewList")
    public ResponseResult articleOverviewList(@NotNull(message = "用户id不能为空") Long userId){
        log.warn("/collection/articleOverview {}",userId);
        return collectionService.getCollectionArticles(userId);
    }
    @GetMapping("/status")
    public ResponseResult status(@NotNull(message = "用户id不能为空") Long userId,
                                 @NotNull(message = "文章id不能为空") Long articleId){
        log.warn("/collection/status {},{}",userId,articleId);
        return collectionService.getCollectStatus(userId,articleId);
    }
    @PostMapping("/action")
    public ResponseResult action(@RequestBody AddCollectionDto addCollectionDto){
        log.warn("/collection/action {}",addCollectionDto);
        return collectionService.collectAction(addCollectionDto);
    }
}
