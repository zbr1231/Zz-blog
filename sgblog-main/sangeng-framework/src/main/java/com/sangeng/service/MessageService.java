package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Article;
import com.sangeng.domain.entity.Message;

import java.util.List;

public interface MessageService extends IService<Message> {
    ResponseResult listMessage();
    ResponseResult addMessage(Message message);
}
