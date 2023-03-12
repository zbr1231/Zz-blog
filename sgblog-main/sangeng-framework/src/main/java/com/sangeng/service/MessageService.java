package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Article;
import com.sangeng.domain.entity.Message;
import org.json.JSONException;

import java.util.List;

public interface MessageService extends IService<Message> {
    ResponseResult listMessage();
    ResponseResult addMessage(Message message) throws JSONException;

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
     * 批量删除留言管理
     *
     * @param ids 需要删除的留言管理主键集合
     * @return 结果
     */
    public int deleteMessageByIds(Long[] ids);

    /**
     * 删除留言管理信息
     *
     * @param id 留言管理主键
     * @return 结果
     */
    public int deleteMessageById(Long id);
}
