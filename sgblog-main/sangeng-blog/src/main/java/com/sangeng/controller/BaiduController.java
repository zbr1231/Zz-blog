package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.GetSummaryDto;
import com.sangeng.utils.TextProcessUtil;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/baidu")
public class BaiduController {
    @PostMapping("/summary")
    public ResponseResult getSummary(@Valid @RequestBody GetSummaryDto getSummaryDto) throws JSONException, IOException {
        String summary = TextProcessUtil.getSummary(getSummaryDto.getText(), getSummaryDto.getTitle());
        return ResponseResult.okResult(summary);
    }
}
