package com.sangeng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Message;
import com.sangeng.mapper.MessageMapper;
import com.sangeng.service.MessageService;
import com.sangeng.utils.AuditUtil;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Resource
    MessageMapper messageMapper;
    @Autowired
    AuditUtil audit;
    @Override
    public ResponseResult listMessage() {
        List<Message> list = list();
        return ResponseResult.okResult(list);
    }

    @Override
    public ResponseResult addMessage(Message message) throws JSONException {
        //文本审核
        Map<String, String> map = audit.auditText(message.getContent());
        if("0".equals(map.get("pass"))){
            //审核不通过返回信息
            return ResponseResult.errorResult(400,map.get("msg"));
        }
        boolean b = save(message);
        if(!b){
            ResponseResult.errorResult(400,"评论失败");
        }
        return ResponseResult.okResult();
    }
    /**
     * 查询留言管理
     *
     * @param id 留言管理主键
     * @return 留言管理
     */
    @Override
    public Message selectMessageById(Long id)
    {
        return messageMapper.selectMessageById(id);
    }

    /**
     * 查询留言管理列表
     *
     * @param message 留言管理
     * @return 留言管理
     */
    @Override
    public List<Message> selectMessageList(Message message)
    {
        return messageMapper.selectMessageList(message);
    }

    /**
     * 新增留言管理
     *
     * @param message 留言管理
     * @return 结果
     */
    @Override
    public int insertMessage(Message message)
    {
        return messageMapper.insertMessage(message);
    }

    /**
     * 修改留言管理
     *
     * @param message 留言管理
     * @return 结果
     */
    @Override
    public int updateMessage(Message message)
    {
        return messageMapper.updateMessage(message);
    }

    /**
     * 批量删除留言管理
     *
     * @param ids 需要删除的留言管理主键
     * @return 结果
     */
    @Override
    public int deleteMessageByIds(Long[] ids)
    {
        return messageMapper.deleteMessageByIds(ids);
    }

    /**
     * 删除留言管理信息
     *
     * @param id 留言管理主键
     * @return 结果
     */
    @Override
    public int deleteMessageById(Long id)
    {
        return messageMapper.deleteMessageById(id);
    }
}
