package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.UnPassedInfo;
import com.sangeng.mapper.UnPassedInfoMapper;
import com.sangeng.service.UnPassedInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UnPassedInfoServiceImpl extends ServiceImpl<UnPassedInfoMapper, UnPassedInfo> implements UnPassedInfoService {

    @Resource
    private UnPassedInfoMapper unPassedInfoMapper;
    @Override
    public ResponseResult add(UnPassedInfo unPassedInfo) {
        save(unPassedInfo);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getByArticleId(Long articleId) {
        UnPassedInfo unPassedInfo = unPassedInfoMapper.getLastOne(articleId);
        return ResponseResult.okResult(unPassedInfo);
    }
}
