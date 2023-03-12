package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddInterestDto {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 标签id
     */
    private Long tagId;
    /**
     * 兴趣标签状态
     */
    private int status;
}
