package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class InterestVo {
    private Long id;
    //标签名
    private String name;
    //兴趣标签状态 1已添加  0未添加
    private int status;
}
