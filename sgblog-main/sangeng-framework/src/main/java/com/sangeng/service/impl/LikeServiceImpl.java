package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddLikeDto;
import com.sangeng.domain.entity.Like;
import com.sangeng.mapper.LikeMapper;
import com.sangeng.service.LikeService;
import com.sangeng.utils.RedisCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zbr
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Resource
    LikeMapper likeMapper;
    @Resource
    RedisCache redisCache;
    @Override
    public ResponseResult saveLikes(List<AddLikeDto> likes) {
        likes.stream().forEach(like ->{
            Long userId = like.getUserId();
            Long articleId = like.getArticleId();
            int status = like.getStatus();
            if(status==-1){
                QueryWrapper<Like> wrapper = new QueryWrapper<>();
                wrapper.eq("user_id",userId);
                wrapper.eq("article_id",articleId);
                likeMapper.delete(wrapper);
            }
            if(status==0){
                likeMapper.insert(Like.builder().userId(userId).articleId(articleId).build());
            }
        });

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult status(Long userId, Long articleId) {
        Integer status = redisCache.getCacheMapValue("article:likeCountUserChange", articleId+":"+userId);
        //status 0或null未操作过缓存，1添加过记录，-1删除过记录三种情况
        if(status==null||status==0){
            //查询数据库得到状态
            QueryWrapper<Like> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            wrapper.eq("article_id", articleId);
            List<Like> likes = likeMapper.selectList(wrapper);
            if (likes.isEmpty()){
                return ResponseResult.okResult(0);
            }
            return ResponseResult.okResult(1);
        }
        if(status==-1){
            return ResponseResult.okResult(0);
        }
        return ResponseResult.okResult(1);
    }


}
