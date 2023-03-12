package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.Follow;
import org.apache.ibatis.annotations.Select;

public interface FollowMapper extends BaseMapper<Follow> {
    @Select("select count(1) from chat_follow")
    Long count();
}
