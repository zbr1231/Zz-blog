package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.UnPassedInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UnPassedInfoMapper extends BaseMapper<UnPassedInfo> {
    @Select("select * from sg_unpassed_info where article_id = #{articleId} order by create_time desc limit 1")
    UnPassedInfo getLastOne(@Param("articleId")Long articleId);
}
