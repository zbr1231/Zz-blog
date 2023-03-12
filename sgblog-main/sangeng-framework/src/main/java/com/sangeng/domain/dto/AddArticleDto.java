package com.sangeng.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddArticleDto {

    private Long id;
    @NotNull(message = "用户id不能为空")
    //用户id
    private Long userId;
    @NotBlank(message = "文章标题不能为空")
    //标题
    private String title;
    @NotBlank(message = "文章内容不能为空")
    //文章内容
    private String content;
    @NotBlank(message = "文章摘要不能为空")
    //文章摘要
    private String summary;
    @NotNull(message = "文章分类不能为空")
    //所属分类id
    private Long categoryId;
    //缩略图
    private String thumbnail;
    @NotNull(message = "置顶状态不能为空")
    //是否置顶（0否，1是）
    private String isTop;
    @NotNull(message = "草稿状态不能为空")
    //状态（0已发布，1草稿）
    private String status;
    //访问量
    private Long viewCount;
    //是否允许评论 1是，0否
    private String isComment;
    private List<Long> tags;

}
