package com.sangeng.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 行为日志表(Article)表实体类
 *
 * @author zbr
 * @since 2022-02-01 11:36:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_action_log")
@Builder
@Accessors(chain = true)
public class ActionLog {
    @TableId
    private Long id;
    @NotNull(message = "用户id不能为空")
    private Long userId;
    @NotNull(message = "文章id不能为空")
    private Long articleId;
    @NotBlank(message = "日志行为不能为空")
    private String action;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}
