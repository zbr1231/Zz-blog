package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.UnPassedInfo;

public interface UnPassedInfoService {
    ResponseResult add(UnPassedInfo unPassedInfo);
    ResponseResult getByArticleId(Long articleId);
}
