package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.ActionLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zbr
 */
public interface ActionLogMapper extends BaseMapper<ActionLog> {
    @Select("select count(1) from sg_action_log")
    Long count();
}
