package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.Article;
import com.sangeng.domain.entity.ArticleSort;
import com.sangeng.domain.entity.Recommend;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RecommendMapper extends BaseMapper<Recommend> {
    //根据用户浏览量降序查询推荐
    public List<ArticleSort> sortByBrowse(@Param("userId") Long userId,@Param("articleId")Long articleId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
    //根据收藏查询推荐
    public List<ArticleSort> sortByCollection(@Param("userId") Long userId,@Param("articleId")Long articleId, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
    //根据关注的人的文章推荐分数推荐
    @Select("SELECT\n" +
            "\tsr.id,\n" +
            "\tsr.article_id,\n" +
            "\tsr.user_id,\n" +
            "\tsr.score \n" +
            "FROM\n" +
            "\tsg_recommend sr left join chat_follow cf on sr.user_id = cf.follow_id\n" +
            "WHERE\n" +
            "\tsr.user_id != #{userId} and cf.user_id = #{userId} order by score desc")
    public List<Recommend> getBigDataRecommend(@Param("userId") Long userId);

    @Select("select count(1) from sg_recommend")
    Long count();
    /**
     * 查询推荐管理
     *
     * @param id 推荐管理主键
     * @return 推荐管理
     */
    public Recommend selectRecommendById(Long id);

    /**
     * 查询推荐管理列表
     *
     * @param recommend 推荐管理
     * @return 推荐管理集合
     */
    public List<Recommend> selectRecommendList(Recommend recommend);

    /**
     * 新增推荐管理
     *
     * @param recommend 推荐管理
     * @return 结果
     */
    public int insertRecommend(Recommend recommend);

    /**
     * 修改推荐管理
     *
     * @param recommend 推荐管理
     * @return 结果
     */
    public int updateRecommend(Recommend recommend);

    /**
     * 删除推荐管理
     *
     * @param id 推荐管理主键
     * @return 结果
     */
    public int deleteRecommendById(Long id);

    /**
     * 批量删除推荐管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRecommendByIds(Long[] ids);

}
