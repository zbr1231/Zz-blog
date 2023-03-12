package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.Message;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {
    @Select("select count(1) from sg_message")
    Long count();
    /**
     * 查询留言管理
     *
     * @param id 留言管理主键
     * @return 留言管理
     */
    public Message selectMessageById(Long id);

    /**
     * 查询留言管理列表
     *
     * @param message 留言管理
     * @return 留言管理集合
     */
    public List<Message> selectMessageList(Message message);

    /**
     * 新增留言管理
     *
     * @param message 留言管理
     * @return 结果
     */
    public int insertMessage(Message message);

    /**
     * 修改留言管理
     *
     * @param message 留言管理
     * @return 结果
     */
    public int updateMessage(Message message);

    /**
     * 删除留言管理
     *
     * @param id 留言管理主键
     * @return 结果
     */
    public int deleteMessageById(Long id);

    /**
     * 批量删除留言管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMessageByIds(Long[] ids);
}
