package com.sangeng.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
public class AnouncementVo
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户id */
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 公告标题 */
    private String title;

    /** 公告详情 */
    private String content;

    /**创建时间 */
    private Date createTime;

    /** 删除标记 */
    private Long delFlag;


}
