package com.sangeng.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "zz-article")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ArticleForEs {
    @Id
    private Long id;
    //标题
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    //文章摘要
    private String summary;

    // 分类, 不分词
    @Field(type = FieldType.Keyword)
    private String categoryName;
    //缩略图
    private String thumbnail;
    //访问量 不入es
    private Long viewCount;
    //点赞数 不入es
    private Long likeCount;
    //是否置顶
    private String isTop;

    //用户id
    private Long userId;

    //用户名
    @Field(type = FieldType.Keyword)
    private String userName;

    private Date createTime;


}
