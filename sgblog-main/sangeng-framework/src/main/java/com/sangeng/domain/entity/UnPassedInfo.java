package com.sangeng.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 文章未审核通过原因实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sg_unpassed_info")
public class UnPassedInfo {
    private Long id;
    private Long articleId;
    //审核未通过原因
    private String message;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
