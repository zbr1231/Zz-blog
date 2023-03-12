package com.sangeng.controller;

import com.sangeng.common.ruoyi.AjaxResult;
import com.sangeng.common.ruoyi.BaseController;
import com.sangeng.common.ruoyi.TableDataInfo;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.CategoryTag;
import com.sangeng.domain.vo.TagVo;
import com.sangeng.service.ICategoryTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 分类标签管理Controller
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@RestController
@RequestMapping("/system/tag")
public class CategoryTagController extends BaseController
{
    @Autowired
    private ICategoryTagService categoryTagService;

    /**
     * 查询分类标签管理列表
     */
    @GetMapping("/list")
    public ResponseResult list(CategoryTag categoryTag)
    {
        startPage();
        List<CategoryTag> list = categoryTagService.selectCategoryTagList(categoryTag);
        return ResponseResult.okResult(getDataTable(list));
    }

    @GetMapping("/restTags")
    public ResponseResult getRestTags(){
        List<TagVo> tagVos = categoryTagService.restTags();
        return ResponseResult.okResult(tagVos);
    }

    /**
     * 获取分类标签管理详细信息
     */
    @GetMapping(value = "/{tagId}")
    public ResponseResult getInfo(@PathVariable("tagId") Long tagId)
    {
        return ResponseResult.okResult(AjaxResult.success(categoryTagService.selectCategoryTagByTagId(tagId)));
    }

    /**
     * 新增分类标签管理
     */
    @PostMapping
    public ResponseResult add(@RequestBody CategoryTag categoryTag)
    {
        return ResponseResult.okResult(toAjax(categoryTagService.insertCategoryTag(categoryTag)));
    }

    /**
     * 修改分类标签管理
     */
    @PutMapping
    public ResponseResult edit(@RequestBody CategoryTag categoryTag)
    {
        return ResponseResult.okResult(toAjax(categoryTagService.updateCategoryTag(categoryTag)));
    }

    /**
     * 删除分类标签管理
     */
	@DeleteMapping("/{tagIds}")
    public ResponseResult remove(@PathVariable Long[] tagIds)
    {
        return ResponseResult.okResult(toAjax(categoryTagService.deleteCategoryTagByTagIds(tagIds)));
    }
}
