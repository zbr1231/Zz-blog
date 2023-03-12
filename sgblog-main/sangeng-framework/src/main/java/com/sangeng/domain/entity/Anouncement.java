package com.sangeng.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * anouncement对象 sg_anouncement
 * 
 * @author zz
 * @date 2023-02-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_anouncement")
@Accessors(chain = true)
public class Anouncement
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;
//    @NotNull(message = "用户id不能为空")
    /** 用户id */
    private Long userId;
//    @NotNull(message = "标题不能为空")
    /** 公告标题 */
    private String title;
//    @NotNull(message = "内容不能为空")
    /** 公告详情 */
    private String content;

    /**创建时间 */
    private Date createTime;

    /** 删除标记 */
    private Long delFlag;


}
