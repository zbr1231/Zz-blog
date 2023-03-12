package com.sangeng.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {
    private Long id;
    //标题
    private String title;

    //简要
    private String summary;

    //封面
    private String thumbnail;

    //访问量
    private Long viewCount;
}
