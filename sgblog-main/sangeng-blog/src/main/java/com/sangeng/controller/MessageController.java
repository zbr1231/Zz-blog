package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Message;
import com.sangeng.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;
    @PostMapping
    public ResponseResult add(@RequestBody Message message) {
        System.out.println("==============");
        messageService.addMessage(message);
        return ResponseResult.okResult();
    }
    @GetMapping
    public ResponseResult list(){
        return messageService.listMessage();
    }
}
