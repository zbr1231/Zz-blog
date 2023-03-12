package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.Interest;
import org.apache.ibatis.annotations.Select;

public interface UserTagMapper extends BaseMapper<Interest> {
    @Select("select count(1) from sg_user_tag")
    Long count();
}
