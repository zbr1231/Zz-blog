package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.Collection;
import org.apache.ibatis.annotations.Select;

public interface CollectionMapper extends BaseMapper<Collection> {
    @Select("select count(1) from sg_collection")
    Long count();
}
