package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCollectionDto {
    @NotNull(message = "用户id不能为空")
    /**
     * 用户id
     */
    private Long userId;
    @NotNull(message = "文章id不能为空")
    /**
     * 文章id
     */
    private Long articleId;
    @NotNull(message = "收藏状态不能为空")
    /**
     * 收藏状态 1 收藏 0 未收藏
     */
    private int status;
}
