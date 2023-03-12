package com.sangeng.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddArticleDto;
import com.sangeng.domain.dto.AddLikeDto;
import com.sangeng.domain.dto.ArticleDto;
import com.sangeng.domain.entity.Article;
import com.sangeng.domain.entity.ArticleTag;
import com.sangeng.service.ArticleService;
import com.sangeng.service.ArticleTagService;
import com.sangeng.service.UnPassedInfoService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
@RequestMapping("/article")
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Resource
    private ArticleTagService articleTagService;
    @Resource
    private UnPassedInfoService unPassedInfoService;

    @PostMapping
    public ResponseResult add(@Valid @RequestBody AddArticleDto article) throws JSONException {
        log.warn("/article/add {}",article);
        return articleService.add(article);
    }
    @GetMapping("/getUserArticles")
    public ResponseResult getUserArticles(@NotNull(message = "页数不能为空") Integer pageNum,
                                          @NotNull(message = "页大小不能为空") Integer pageSize,
                                          @NotNull(message = "用户id不能为空") Long userId){
        return articleService.articleListByUserId(pageNum,pageSize,userId);
    }
    @GetMapping("/getUserDrafts")
    public ResponseResult getUserDrafts(@NotNull(message = "用户id不能为空") Long userId){
        return articleService.getUserDrafts(userId);
    }

    @GetMapping("/getRencentArticles")
    public ResponseResult getRencentArticles(@NotNull(message = "用户id不能为空") Long userId){
        return articleService.getRecentBrowse(userId);
    }

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){

        ResponseResult result =  articleService.hotArticleList();
        return result;
    }

    @GetMapping("/articleList")
    public ResponseResult articleList(@NotNull(message = "页数不能为空") Integer pageNum,
                                      @NotNull(message = "页大小不能为空") Integer pageSize,
                                      @NotNull(message = "分类id不能为空") Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/getRecycling")
    public ResponseResult getRecycling(@NotNull(message = "用户id不能为空") Long userId){
        return articleService.getRecycling(userId);
    }

    @GetMapping("/getArticlesByTag")
    public ResponseResult getArticlesByCategoryTag(@NotNull(message = "页数不能为空") Integer pageNum,
                                                   @NotNull(message = "页大小不能为空") Integer pageSize,
                                                   @NotNull(message = "标签id不能为空") Long tagId){
        return articleService.articleListByTag(pageNum,pageSize,tagId);
    }


    @PutMapping("/updateViewCount/{userId}/{articleId}")
    public ResponseResult updateViewCount(@NotNull(message = "用户id不能为空") @PathVariable("userId") Long userId,
                                          @NotNull(message = "文章id不能为空") @PathVariable("articleId") Long articleId){
        log.warn("/article/updateViewCount {},{}",userId,articleId);
        return articleService.updateViewCount(userId,articleId);
    }

    @PostMapping("/updateLikeCount")
    public ResponseResult updateLikeCount(@Valid @RequestBody AddLikeDto addLikeDto){
        log.warn("/article/updateLikeCount {}",addLikeDto);
        return articleService.updateLikeCount(addLikeDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@NotNull(message = "文章id不能为空") @PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    @DeleteMapping("/logic/{id}")
    public ResponseResult logicDelete(@NotNull(message = "文章id不能为空") @PathVariable Long id){
        articleService.logicDelete(id);
        return ResponseResult.okResult();
    }
    @DeleteMapping("/{id}")
    public ResponseResult delete(@NotNull(message = "文章id不能为空") @PathVariable Long id){
        articleService.delete(id);
        return ResponseResult.okResult();
    }
    @PutMapping
    public ResponseResult edit(@RequestBody ArticleDto article){
        articleService.edit(article);
        return ResponseResult.okResult();
    }
    @GetMapping("/unpassInfo")
    public ResponseResult unpassInfo(@NotNull(message = "文章id不能为空") Long articleId){
        return unPassedInfoService.getByArticleId(articleId);
    }
}
