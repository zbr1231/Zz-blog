package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.Like;
import org.apache.ibatis.annotations.Select;

public interface LikeMapper extends BaseMapper<Like> {
    @Select("select count(1) from sg_like")
    Long count();
}
