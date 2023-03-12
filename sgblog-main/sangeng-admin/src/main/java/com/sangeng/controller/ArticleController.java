package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddArticleDto;
import com.sangeng.domain.dto.ArticleDto;
import com.sangeng.domain.entity.Article;
import com.sangeng.domain.entity.UnPassedInfo;
import com.sangeng.domain.vo.ArticleVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.service.ArticleService;
import com.sangeng.service.CategoryService;
import com.sangeng.service.UnPassedInfoService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Resource
    private UnPassedInfoService unPassedInfoService;
    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article) throws JSONException {
        return articleService.add(article);
    }


    @GetMapping("/list")
    public ResponseResult list(Article article, Integer pageNum, Integer pageSize)
    {
        PageVo pageVo = articleService.selectArticlePage(article,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }

    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        ArticleVo article = articleService.getInfo(id);
        return ResponseResult.okResult(article);
    }

    @PutMapping
    public ResponseResult edit(@RequestBody ArticleDto article){
        articleService.edit(article);
        return ResponseResult.okResult();
    }
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id){
        articleService.delete(id);
        return ResponseResult.okResult();
    }
    /**
     * 修改文章置顶状态
     */
    @PutMapping("/changeTop")
    public ResponseResult changeStatus(@RequestBody Map<String,String> map){
        String articleId = map.get("articleId");
        String isTop = map.get("isTop");
        if(articleId==null || isTop==null){
            return ResponseResult.errorResult(402,"文章id或置顶信息不能为空");
        }
        return articleService.changeArticleTop(Long.valueOf(articleId),isTop);
    }

    /**
     * 文章未审核信息
     * @param unPassedInfo
     * @return
     */
    @PutMapping("/action/unpassInfo")
    public ResponseResult unpass(@RequestBody UnPassedInfo unPassedInfo){
        return unPassedInfoService.add(unPassedInfo);
    }
    @GetMapping("/unpassInfo")
    public ResponseResult unpassInfo(Long articleId){
        return unPassedInfoService.getByArticleId(articleId);
    }
}