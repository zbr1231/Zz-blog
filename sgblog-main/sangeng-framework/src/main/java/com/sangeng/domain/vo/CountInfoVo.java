package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountInfoVo {
    //文章总浏览量
    private Long totalViewCount;
    //文章数量
    private int articleCount;
    //粉丝人数
    private int followerCount;
}
