package com.sangeng.service;

import com.sangeng.domain.entity.Anouncement;
import com.sangeng.domain.vo.AnouncementVo;

import java.util.List;

/**
 * anouncementService接口
 * 
 * @author zz
 * @date 2023-02-14
 */
public interface IAnouncementService 
{
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
    public List<AnouncementVo> selectAnouncementList(Anouncement anouncement);

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
     * 批量删除anouncement
     * 
     * @param ids 需要删除的anouncement主键集合
     * @return 结果
     */
    public int deleteAnouncementByIds(Long[] ids);

    /**
     * 删除anouncement信息
     * 
     * @param id anouncement主键
     * @return 结果
     */
    public int deleteAnouncementById(Long id);

    boolean updateAnouncestatus(Long anouncementId, String status);
}
