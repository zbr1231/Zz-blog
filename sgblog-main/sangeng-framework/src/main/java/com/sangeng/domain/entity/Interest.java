package com.sangeng.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 兴趣标签表表实体类
 *
 * @author zbr
 * @since 2022-02-01 11:36:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_user_tag")
@Accessors(chain = true)
public class Interests {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 标签id
     */
    private Long tagId;
}
