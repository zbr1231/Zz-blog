package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.ActionLog;
import com.sangeng.service.ActionLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RequestMapping("/action")
@RestController
@Slf4j
public class ActionLogController {
    @Resource
    ActionLogService actionLogService;
    @PutMapping("/log")
    public ResponseResult log(@Valid @RequestBody ActionLog actionLog){
        log.warn("/action/log {}", actionLog);
        return actionLogService.addLog(actionLog);
    }
}
