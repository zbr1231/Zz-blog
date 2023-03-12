package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Recommend;
import com.sangeng.domain.vo.HotArticleVo;

import java.util.List;

public interface RecommendService {
    ResponseResult detailPageRecommend(Long userId,Long articleId);
    ResponseResult interestRecommend(Long userId,Long articleId);
    List<HotArticleVo> baseRecommend(Long userId, Long articleId);
    ResponseResult bigDataRecommend(Long userId);

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
     * 批量删除推荐管理
     *
     * @param ids 需要删除的推荐管理主键集合
     * @return 结果
     */
    public int deleteRecommendByIds(Long[] ids);

    /**
     * 删除推荐管理信息
     *
     * @param id 推荐管理主键
     * @return 结果
     */
    public int deleteRecommendById(Long id);
}
