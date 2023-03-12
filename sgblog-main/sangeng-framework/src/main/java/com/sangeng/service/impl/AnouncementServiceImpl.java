package com.sangeng.service.impl;


import com.sangeng.common.ruoyi.DateUtils;
import com.sangeng.domain.entity.Anouncement;
import com.sangeng.domain.entity.User;
import com.sangeng.domain.vo.AnouncementVo;
import com.sangeng.mapper.AnouncementMapper;
import com.sangeng.service.IAnouncementService;
import com.sangeng.service.UserService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * anouncementService业务层处理
 * 
 * @author zz
 * @date 2023-02-14
 */
@Service
public class AnouncementServiceImpl implements IAnouncementService
{
    @Autowired
    private AnouncementMapper anouncementMapper;
    @Resource
    private UserService userService;
    /**
     * 查询anouncement
     * 
     * @param id anouncement主键
     * @return anouncement
     */
    @Override
    public Anouncement selectAnouncementById(Long id)
    {
        return anouncementMapper.selectAnouncementById(id);
    }

    /**
     * 查询anouncement列表
     * 
     * @param anouncement anouncement
     * @return anouncement
     */
    @Override
    public List<AnouncementVo> selectAnouncementList(Anouncement anouncement)
    {
        List<Anouncement> anouncements = anouncementMapper.selectAnouncementList(anouncement);
        List<AnouncementVo> anouncementVos = BeanCopyUtils.copyBeanList(anouncements, AnouncementVo.class);
        anouncementVos.stream().map(anouncementVo -> {
            User user = userService.getById(anouncementVo.getUserId());
            anouncementVo.setUserName(user.getUserName());
            return anouncementVo;
        }).collect(Collectors.toList());
        return anouncementVos;
    }

    /**
     * 新增anouncement
     * 
     * @param anouncement anouncement
     * @return 结果
     */
    @Override
    public int insertAnouncement(Anouncement anouncement)
    {
        anouncement.setCreateTime(DateUtils.getNowDate());
        return anouncementMapper.insertAnouncement(anouncement);
    }

    /**
     * 修改anouncement
     * 
     * @param anouncement anouncement
     * @return 结果
     */
    @Override
    public int updateAnouncement(Anouncement anouncement)
    {
        return anouncementMapper.updateAnouncement(anouncement);
    }

    /**
     * 批量删除anouncement
     * 
     * @param ids 需要删除的anouncement主键
     * @return 结果
     */
    @Override
    public int deleteAnouncementByIds(Long[] ids)
    {
        return anouncementMapper.deleteAnouncementByIds(ids);
    }

    /**
     * 删除anouncement信息
     * 
     * @param id anouncement主键
     * @return 结果
     */
    @Override
    public int deleteAnouncementById(Long id)
    {
        return anouncementMapper.deleteAnouncementById(id);
    }

    @Override
    public boolean updateAnouncestatus(Long anouncementId, String status) {
        status = String.valueOf(status);
        return anouncementMapper.updateStatus(anouncementId,status)>0;
    }
}
