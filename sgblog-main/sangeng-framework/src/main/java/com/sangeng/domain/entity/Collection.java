package com.sangeng.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 收藏表(ChatFollow)表实体类
 *
 * @author zbr
 * @since 2022-02-01 11:36:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_collection")
@Accessors(chain = true)
public class Collection {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 文章id
     */
    private Long articleId;
}
