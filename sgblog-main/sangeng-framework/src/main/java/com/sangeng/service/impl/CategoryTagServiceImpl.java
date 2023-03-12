package com.sangeng.service.impl;


import com.sangeng.domain.entity.CategoryTag;
import com.sangeng.domain.vo.TagVo;
import com.sangeng.mapper.CategoryTagMapper;
import com.sangeng.service.CategoryService;
import com.sangeng.service.ICategoryTagService;
import com.sangeng.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分类标签管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class CategoryTagServiceImpl implements ICategoryTagService
{
    @Resource
    private CategoryTagMapper categoryTagMapper;
    @Resource
    private CategoryService categoryService;
    @Resource
    private TagService tagService;
    /**
     * 查询分类标签管理
     * 
     * @param tagId 分类标签管理主键
     * @return 分类标签管理
     */
    @Override
    public CategoryTag selectCategoryTagByTagId(Long tagId)
    {
        return categoryTagMapper.selectCategoryTagByTagId(tagId);
    }

    /**
     * 查询分类标签管理列表
     * 
     * @param categoryTag 分类标签管理
     * @return 分类标签管理
     */
    @Override
    public List<CategoryTag> selectCategoryTagList(CategoryTag categoryTag)
    {
        Map<Long,String> categoryMap = new HashMap<Long,String>();
        Map<Long,String> tagMap = new HashMap<Long,String>();
        List<CategoryTag> categoryTags = categoryTagMapper.selectCategoryTagList(categoryTag);
        categoryTags.stream().map(vo -> {
            Long categoryId = vo.getCategoryId();
            Long tagId = vo.getTagId();
            String categoryName = categoryMap.get(vo.getCategoryId());
            String tagName = categoryMap.get(vo.getTagId());
            if(categoryName == null){
                categoryName = categoryService.getById(categoryId).getName();
                categoryMap.put(categoryId,categoryName);
            }
            if(tagName == null){
                tagName = tagService.getById(tagId).getName();
                tagMap.put(tagId,tagName);
            }
            vo.setCategoryName(categoryName);
            vo.setTagName(tagName);
            return vo;
        }).collect(Collectors.toList());

        return categoryTags;
    }

    /**
     * 新增分类标签管理
     * 
     * @param categoryTag 分类标签管理
     * @return 结果
     */
    @Override
    public int insertCategoryTag(CategoryTag categoryTag)
    {
        return categoryTagMapper.insertCategoryTag(categoryTag);
    }

    /**
     * 修改分类标签管理
     * 
     * @param categoryTag 分类标签管理
     * @return 结果
     */
    @Override
    public int updateCategoryTag(CategoryTag categoryTag)
    {
        return categoryTagMapper.updateCategoryTag(categoryTag);
    }

    /**
     * 批量删除分类标签管理
     * 
     * @param tagIds 需要删除的分类标签管理主键
     * @return 结果
     */
    @Override
    public int deleteCategoryTagByTagIds(Long[] tagIds)
    {
        return categoryTagMapper.deleteCategoryTagByTagIds(tagIds);
    }

    /**
     * 删除分类标签管理信息
     * 
     * @param tagId 分类标签管理主键
     * @return 结果
     */
    @Override
    public int deleteCategoryTagByTagId(Long tagId)
    {
        return categoryTagMapper.deleteCategoryTagByTagId(tagId);
    }

    @Override
    public List<TagVo> restTags() {
        return categoryTagMapper.getRestTags();
    }
}
