package com.sangeng.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author zbr
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sg_like")
@Accessors(chain = true)
public class Like {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 文章id
     */
    private Long articleId;
}
