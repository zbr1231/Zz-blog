package com.sangeng.mapper;


import com.sangeng.domain.UserTest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TestMapper {
    @Select({"select * from user limit 10"})
    List<UserTest> find();
    @Insert({"insert into user values (#{id},#{name},#{city},#{tel})"})
    int save(@Param("id") int id,@Param("name") String name,@Param("city") String city,@Param("tel") String tel);
    @Update({"update user set name='zz' where id = #{id}"})
    int update(@Param("id")int id);

}
