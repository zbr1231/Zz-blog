package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.ArticleTag;
import com.sangeng.domain.entity.CategoryTag;
import com.sangeng.domain.vo.TagVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryTagMapper extends BaseMapper<CategoryTag> {
    @Select("select count(1) from sg_category_tag")
    Long count();

    /**
     * 查询未被分配类别的标签
     * @return
     */
    @Select("select * from sg_tag where id not in (SELECT tag_id FROM `sg_category_tag`)")
    List<TagVo> getRestTags();
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
     * 删除分类标签管理
     *
     * @param tagId 分类标签管理主键
     * @return 结果
     */
    public int deleteCategoryTagByTagId(Long tagId);

    /**
     * 批量删除分类标签管理
     *
     * @param tagIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCategoryTagByTagIds(Long[] tagIds);
}
