package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFollowDto {
    /**
     * 当前用户id
     */
    private Long userId;
    /**
     * 被关注用户id
     */
    private Long followId;
    /**
     * 关注状态
     */
    private int status;
}
