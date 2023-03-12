package com.sangeng.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author zbr
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_category_tag")
@Accessors(chain = true)
public class CategoryTag {
    private Long categoryId;
    private Long tagId;
    @TableField(exist = false)
    private String categoryName;
    @TableField(exist = false)
    private String tagName;
}
