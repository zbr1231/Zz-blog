package com.sangeng.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户简介
     */
    private String description;
    /**
     * 性别
     */
    private String sex;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 注册时间
     */
    private Date createTime;
    /**
     * 用户信息更新时间
     */
    private Date updateTime;

}