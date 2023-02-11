package com.sangeng.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 关注表(ChatFollow)表实体类
 *
 * @author zbr
 * @since 2022-02-01 11:36:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("chat_follow")
@Accessors(chain = true)
public class ChatFollow {
    /**
     * 当前用户id
     */
    private Long userId;
    /**
     * 被关注用户id
     */
    private Long followId;
}
