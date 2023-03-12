package com.sangeng.mapper;

import com.sangeng.domain.entity.Anouncement;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * anouncementMapper接口
 * 
 * @author zz
 * @date 2023-02-14
 */
public interface AnouncementMapper 
{

    @Update("update `sg_anouncement` set del_flag = #{status} where id = #{anouncementId}")
    int updateStatus(@Param("anouncementId") Long anouncementId, @Param("status") String status);
    /**
     * 查询anouncement
     * 
     * @param id anouncement主键
     * @return anouncement
     */
    public Anouncement selectAnouncementById(Long id);

    /**
     * 查询anouncement列表
     * 
     * @param anouncement anouncement
     * @return anouncement集合
     */
    public List<Anouncement> selectAnouncementList(Anouncement anouncement);

    /**
     * 新增anouncement
     * 
     * @param anouncement anouncement
     * @return 结果
     */
    public int insertAnouncement(Anouncement anouncement);

    /**
     * 修改anouncement
     * 
     * @param anouncement anouncement
     * @return 结果
     */
    public int updateAnouncement(Anouncement anouncement);

    /**
     * 删除anouncement
     * 
     * @param id anouncement主键
     * @return 结果
     */
    public int deleteAnouncementById(Long id);

    /**
     * 批量删除anouncement
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAnouncementByIds(Long[] ids);
}
