package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddInterestDto;
import com.sangeng.domain.dto.TagListDto;
import com.sangeng.domain.entity.ArticleTag;
import com.sangeng.domain.entity.CategoryTag;
import com.sangeng.domain.entity.Interest;
import com.sangeng.domain.entity.Tag;
import com.sangeng.domain.vo.InterestVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.TagVo;
import com.sangeng.mapper.ArticleTagMapper;
import com.sangeng.mapper.CategoryTagMapper;
import com.sangeng.mapper.TagMapper;
import com.sangeng.mapper.UserTagMapper;
import com.sangeng.service.TagService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-07-19 22:33:38
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Resource
    UserTagMapper userTagMapper;
    @Resource
    TagMapper tagMapper;
    @Resource
    ArticleTagMapper articleTagMapper;
    @Resource
    CategoryTagMapper categoryTagMapper;
    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setSearchCount(false);
        page(page, queryWrapper);
        Long total = tagMapper.count();
        //封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(),total);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return tagVos;
    }

    @Override
    public ResponseResult getInterestTags(Long userId) {
        QueryWrapper<Interest> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<Interest> interests = userTagMapper.selectList(wrapper);
        List<Tag> collect = interests.stream().map(interest -> {
            QueryWrapper<Tag> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("id",interest.getTagId());
            return baseMapper.selectOne(wrapper1);
        }).collect(Collectors.toList());
        return ResponseResult.okResult(collect);
    }

    @Override
    public ResponseResult listAllInterestTags(Long userId) {
        List<TagVo> tags = listAllTag();
        List<InterestVo> res = tags.stream().map(tag -> {
            //根据tagId查询用户是否添加此标签
            QueryWrapper<Interest> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            wrapper.eq("tag_id", tag.getId());
            Interest interest = userTagMapper.selectOne(wrapper);
            int status = 0;
            if (interest != null) {
                status = 1;
            }
            return InterestVo.builder()
                    .id(tag.getId())
                    .name(tag.getName())
                    .status(status)
                    .build();
        }).collect(Collectors.toList());
        return ResponseResult.okResult(res);
    }

    @Override
    public ResponseResult listByCategoryId(Long categoryId) {
        QueryWrapper<CategoryTag> wrapper = new QueryWrapper<CategoryTag>();
        wrapper.eq("category_id",categoryId);
        List<CategoryTag> categoryTags = categoryTagMapper.selectList(wrapper);
        List<Tag> tags = categoryTags.stream()
                .map(categoryTag -> tagMapper.selectById(categoryTag.getTagId()))
                .collect(Collectors.toList());
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tags, TagVo.class);
        tagVos.stream().map(tagVo -> {
            tagVo.setCount(articleTagMapper.getArticleCount(tagVo.getId()));
            return tagVo;
        }).collect(Collectors.toList());
        return ResponseResult.okResult(tagVos);
    }

    @Override
    public ResponseResult interestAction(AddInterestDto addInterestDto) {
        Long userId = addInterestDto.getUserId();
        Long tagId = addInterestDto.getTagId();
        int status = addInterestDto.getStatus();
        if(status==1){
            QueryWrapper<Interest> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            wrapper.eq("tag_id", tagId);
            userTagMapper.delete(wrapper);
        }else{
            userTagMapper.insert(Interest
                    .builder()
                    .userId(userId)
                    .tagId(tagId)
                    .build());
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getByArticleId(Long articleId) {
        QueryWrapper<ArticleTag> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id",articleId);
        List<ArticleTag> articleTags = articleTagMapper.selectList(wrapper);
        List<Tag> list = articleTags.stream()
                .map(articleTag -> tagMapper.selectById(articleTag.getTagId()))
                .collect(Collectors.toList());
        return ResponseResult.okResult(list);
    }
}

