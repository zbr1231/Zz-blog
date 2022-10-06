package com.sangeng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Message;
import com.sangeng.mapper.MessageMapper;
import com.sangeng.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Override
    public ResponseResult listMessage() {
        List<Message> list = list();
        return ResponseResult.okResult(list);
    }

    @Override
    public ResponseResult addMessage(Message message) {
        boolean b = save(message);
        if(!b){
            ResponseResult.errorResult(400,"评论失败");
        }
        return ResponseResult.okResult();
    }
}
