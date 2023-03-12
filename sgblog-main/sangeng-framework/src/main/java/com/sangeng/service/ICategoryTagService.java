package com.sangeng.service;

import com.sangeng.domain.entity.CategoryTag;
import com.sangeng.domain.vo.TagVo;

import java.util.List;

/**
 * 分类标签管理Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface ICategoryTagService 
{
    /**
     * 查询分类标签管理
     * 
     * @param tagId 分类标签管理主键
     * @return 分类标签管理
     */
    public CategoryTag selectCategoryTagByTagId(Long tagId);

    /**
     * 查询分类标签管理列表
     * 
     * @param categoryTag 分类标签管理
     * @return 分类标签管理集合
     */
    public List<CategoryTag> selectCategoryTagList(CategoryTag categoryTag);

    /**
     * 新增分类标签管理
     * 
     * @param categoryTag 分类标签管理
     * @return 结果
     */
    public int insertCategoryTag(CategoryTag categoryTag);

    /**
     * 修改分类标签管理
     * 
     * @param categoryTag 分类标签管理
     * @return 结果
     */
    public int updateCategoryTag(CategoryTag categoryTag);

    /**
     * 批量删除分类标签管理
     * 
     * @param tagIds 需要删除的分类标签管理主键集合
     * @return 结果
     */
    public int deleteCategoryTagByTagIds(Long[] tagIds);

    /**
     * 删除分类标签管理信息
     * 
     * @param tagId 分类标签管理主键
     * @return 结果
     */
    public int deleteCategoryTagByTagId(Long tagId);

    /**
     * 获取未分配给类别的标签
     * @return
     */
    public List<TagVo> restTags();
}
