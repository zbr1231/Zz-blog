package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.Category;
import org.apache.ibatis.annotations.Select;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-02 12:31:18
 */
public interface CategoryMapper extends BaseMapper<Category> {
    @Select("select count(1) from sg_category")
    Long count();
}

