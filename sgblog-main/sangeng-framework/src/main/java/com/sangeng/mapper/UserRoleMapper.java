package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.UserRole;
import org.apache.ibatis.annotations.Select;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    @Select("select count(1) from sg_user_role")
    Long count();
}