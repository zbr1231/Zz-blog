package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddLikeDto {
    @NotNull(message = "文章id不能为空")
    //文章id
    private Long articleId;
    @NotNull(message = "用户id不能为空")
    //用户id
    private Long userId;
    @NotNull(message = "状态不能为空")
    private int status;
}
