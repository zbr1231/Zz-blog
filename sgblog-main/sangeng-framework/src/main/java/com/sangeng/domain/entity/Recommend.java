package com.example.hadoop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sg_recommend")
public class Recommend {
    @TableId
    private Long id;
    private Long articleId;
    private Long userId;
    private Double score;
}
