package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.RoleMenu;
import org.apache.ibatis.annotations.Select;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    @Select("select count(1) from sg_role_menu")
    Long count();
}
