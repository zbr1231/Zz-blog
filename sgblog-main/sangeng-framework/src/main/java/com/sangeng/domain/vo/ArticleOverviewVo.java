package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleOverviewVo {
    //文章id
    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //缩略图
    private String thumbnail;

    private String time;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArticleOverviewVo that = (ArticleOverviewVo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, summary, thumbnail);
    }
}
