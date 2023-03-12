package com.sangeng.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSearchDto {
    private Integer pageNum;
    private Integer pageSize;
    private String title;
    private String summary;
    private String userName;
}
