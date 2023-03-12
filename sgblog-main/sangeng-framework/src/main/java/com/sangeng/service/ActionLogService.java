package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.ActionLog;

public interface ActionLogService {
    /**
     * 记录操作日志
     * @param actionLog
     * @return
     */
    ResponseResult addLog(ActionLog actionLog);
}
