package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddInterestDto;
import com.sangeng.domain.dto.AddTagDto;
import com.sangeng.domain.dto.EditTagDto;
import com.sangeng.domain.dto.TagListDto;
import com.sangeng.domain.entity.Tag;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.TagVo;
import com.sangeng.service.TagService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }
    @GetMapping("/interests")
    public ResponseResult interests(Long userId){
        return tagService.getInterestTags(userId);
    }

    @GetMapping("/listAllInterests")
    public ResponseResult listAllIntrerests(Long userId){
        return tagService.listAllInterestTags(userId);
    }
    @PutMapping("/interestAction")
    public ResponseResult interestAction(@RequestBody AddInterestDto addInterestDto){
        return tagService.interestAction(addInterestDto);
    }
    @GetMapping("/getArtcileTags")
    public ResponseResult getArtcileTags(Long articleId){
        return tagService.getByArticleId(articleId);
    }
    @GetMapping("/getCategoryTags")
    public ResponseResult getCategoryTags(Long categoryId){
        return tagService.listByCategoryId(categoryId);
    }
}
