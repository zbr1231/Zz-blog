package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-03 16:25:39
 */
public interface UserMapper extends BaseMapper<User> {
    @Update("update `sys_user` set status = #{status} where id = #{userId}")
    int updateStatus(@Param("userId") Long userId, @Param("status") String status);
    @Select("select count(1) from sys_user")
    Long count();
}

