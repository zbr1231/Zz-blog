package com.sangeng.service.impl;

import com.baidu.aip.contentcensor.AipContentCensor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.ActionLogConstants;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddArticleDto;
import com.sangeng.domain.dto.AddLikeDto;
import com.sangeng.domain.dto.ArticleDto;
import com.sangeng.domain.entity.*;
import com.sangeng.domain.vo.*;
import com.sangeng.mapper.ActionLogMapper;
import com.sangeng.mapper.ArticleMapper;
import com.sangeng.mapper.CategoryTagMapper;
import com.sangeng.mapper.EsArticleMapper;
import com.sangeng.service.*;
import com.sangeng.utils.AuditUtil;
import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.RedisCache;
import com.sangeng.utils.SecurityUtils;
import lombok.Data;
import org.apache.commons.lang.SystemUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    @Resource
    private ArticleService articleService;

    @Autowired
    private AuditUtil audit;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private EsArticleMapper esArticleMapper;


    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page(1,10);
        page.setSearchCount(false);
        page(page,queryWrapper);
        Long total = articleMapper.count();
        List<Article> articles = page.getRecords();
        //bean拷贝
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);
//            articleVos.add(vo);
//        }
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0 ,Article::getCategoryId,categoryId);
        // 状态是正式发布的，非草稿
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 状态为已经审核通过的
        lambdaQueryWrapper.eq(Article::getPassed,SystemConstants.ARTICLE_PASSED);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop,Article::getCreateTime);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page.setSearchCount(false);
        page(page,lambdaQueryWrapper);
        Long total = articleMapper.count();
        List<Article> articles = page.getRecords();
        //查询categoryName,和缓存中的viewCount
        articles.stream()
                .map(article -> {
                    article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
                    article.setUserName(userService.getById(article.getUserId()).getNickName());
                    Integer viewCount = redisCache.getCacheMapValue("article:viewCount",article.getId()+"");
                    if(viewCount == null){
                        viewCount = 0;
                    }
                    article.setViewCount(viewCount.longValue());
                    return article;
                })
                .collect(Collectors.toList());
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos,total);
        return ResponseResult.okResult(pageVo);
    }
    @Override
    public ResponseResult articleListByTag(Integer pageNum, Integer pageSize, Long tagId) {
        //得到分类 分页查询
        Map<Object, Object> map = articleTagService.listByTag(pageNum, pageSize, tagId);
        List<ArticleTag> articleTags = (List<ArticleTag>) map.get("records");
        Long total = (Long) map.get("total");

        List<Article> articles = articleTags.stream().map(articleTag -> {
            QueryWrapper<Article> wrapper = new QueryWrapper<>();
            wrapper.eq("status", 0);
            wrapper.eq("id", articleTag.getArticleId());
            return articleMapper.selectOne(wrapper);
        }).collect(Collectors.toList());
        List<Article> collect = new ArrayList<>();
        //排除空值
        while (articles.contains(null)){
            articles.remove(null);
        }
        System.out.println(articles);
        if(articles.size()>0){
            collect = articles.stream().map(article -> {
                article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
                article.setUserName(userService.getById(article.getUserId()).getNickName());
                Integer viewCount = redisCache.getCacheMapValue("article:viewCount", article.getId() + "");
                if(viewCount==null){
                    viewCount=0;
                }
                article.setViewCount(viewCount.longValue());
                return article;
            }).collect(Collectors.toList());
        }
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(collect, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos,total);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeArticleTop(Long articleId, String isTop) {
        Article article = Article.builder().id(articleId).isTop(isTop).build();
        articleMapper.updateById(article);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult articleListByUserId(Integer pageNum, Integer pageSize, Long userId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>();
        wrapper.eq("user_id", userId);
        Page<Article> page = new Page<>(pageNum,pageSize);
        page.setSearchCount(false);
        page(page,wrapper);
        List<Article> articles = page.getRecords();
        return ResponseResult.okResult(articles);
    }

    @Override
    public Long getCountByCategory(Long categoryId) {
        return articleMapper.getCountByCategory(categoryId);
    }

    @Override
    public ResponseResult getUserDrafts(Long userId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>();
        wrapper.eq("user_id", userId);
        wrapper.eq("status",1);
        List<Article> articles = articleMapper.selectList(wrapper);
        return ResponseResult.okResult(articles);
    }

    @Override
    public ResponseResult getRecentBrowse(Long userId) {
        List<String> ids = redisCache.getCacheList("recentArticleId:" + userId);
        List<String> times = redisCache.getCacheList("recentArticleTime:" + userId);
        List<ArticleOverviewVo> res = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            Long articleId = Long.valueOf(ids.get(i));
            ArticleOverviewVo articleOverview = articleService.getArticleOverview(articleId);
            if(articleOverview!=null){
                articleOverview.setTime(times.get(i));
                res.add(articleOverview);
            }
        }
        return ResponseResult.okResult(res);
    }


    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        Integer likeCount = redisCache.getCacheMapValue("article:likeCount",id.toString());
        if(viewCount == null){
            viewCount = 0;
        }
        if(likeCount == null){
            likeCount = 0;
        }
        article.setViewCount(viewCount.longValue());
        article.setLikeCount(likeCount.longValue());
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        //根据用户id查询用户名
        Long userId = articleDetailVo.getUserId();
        User user = userService.getById(userId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        if(user!=null){
            articleDetailVo.setUserName(user.getNickName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult getRecycling(Long userId) {
        List<Article> articles = articleMapper.getRecyclings(userId);
        List<ArticleOverviewVo> articleOverviewVos = BeanCopyUtils.copyBeanList(articles, ArticleOverviewVo.class);
        return ResponseResult.okResult(articleOverviewVos);
    }

    @Override
    public ArticleOverviewVo getArticleOverview(Long id) {
        return articleMapper.getArticleOverview(id);
    }

    @Override
    public int getCountByUserId(Long userId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        Integer res = articleMapper.selectCount(wrapper);
        return res;
    }

    @Override
    public Long getUserTotalViewCount(Long userId) {
        return articleMapper.getUserTotalCount(userId);
    }

    @Override
    public ResponseResult updateViewCount(Long userId,Long articleId) {
//        if(userId==-1){
//            return ResponseResult.okResult();
//        }
        //避免频繁操作
//        Integer exist = redisCache.getCacheMapValue("repeat:viewCount", userId+":"+articleId);
//        if(exist != null){
//            return ResponseResult.okResult();
//        }
//        redisCache.incrementCacheMapValue("repeat:viewCount", userId+":"+articleId, 1);

        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount",articleId.toString(),1);
        redisCache.incrementCacheMapValue("article:viewCountChange",articleId.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateLikeCount(AddLikeDto addLikeDto) {
        int status = addLikeDto.getStatus();
        Long articleId = addLikeDto.getArticleId();
        Long userId = addLikeDto.getUserId();
        Integer num = redisCache.getCacheMapValue("article:likeCountUserChange", articleId + ":" + userId);
        if(status==0&&num!=null && num.equals(1)){
            return ResponseResult.errorResult(402,"请勿重复点赞");
        }
        //更新redis中对应 id的点赞数
        if(status == 1){
            redisCache.incrementCacheMapValue("article:likeCount",articleId.toString(),-1);
            redisCache.incrementCacheMapValue("article:likeCountChange",articleId.toString(),-1);
            redisCache.incrementCacheMapValue("article:likeCountUserChange",articleId+":"+userId,-1);
        }else {
            redisCache.incrementCacheMapValue("article:likeCount",articleId.toString(),1);
            redisCache.incrementCacheMapValue("article:likeCountChange",articleId.toString(),1);
            redisCache.incrementCacheMapValue("article:likeCountUserChange",articleId+":"+userId,1);

        }
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult add(AddArticleDto articleDto) throws JSONException {
        if(articleDto.getCategoryId() == null) {
            return ResponseResult.errorResult(400,"文章分类不能为空");
        }
        if(articleDto.getTags()==null||articleDto.getTags().size()==0) {
            return ResponseResult.errorResult(400,"文章标签不能为空");
        }
        //添加 博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        //文本审核
        Map<String, String> map = audit.auditText(article.getTitle()+","+article.getSummary()+","+article.getContent());
        if("0".equals(map.get("pass"))){
            //审核不通过返回信息
            return ResponseResult.errorResult(400,map.get("msg"));
        }
        save(article);
        //添加入es
        ArticleForEs articleForEs = BeanCopyUtils.copyBean(article, ArticleForEs.class);
        esArticleMapper.add(articleForEs);
        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }


    @Override
    public PageVo selectArticlePage(Article article, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.hasText(article.getTitle()),Article::getTitle, article.getTitle());
        queryWrapper.like(StringUtils.hasText(article.getSummary()),Article::getSummary, article.getSummary());
        queryWrapper.eq(article.getPassed()!=null,Article::getPassed,article.getPassed());
        Page<Article> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setSearchCount(false);
        page(page,queryWrapper);
        Long total = articleMapper.count();
        //转换成VO
        List<Article> articles = page.getRecords();

        //这里偷懒没写VO的转换 应该转换完在设置到最后的pageVo中

        PageVo pageVo = new PageVo();
        pageVo.setTotal(total);
        pageVo.setRows(articles);
        return pageVo;
    }

    @Override
    public ArticleVo getInfo(Long id) {
        Article article = getById(id);
        //获取关联标签
        LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleTagLambdaQueryWrapper.eq(ArticleTag::getArticleId,article.getId());
        List<ArticleTag> articleTags = articleTagService.list(articleTagLambdaQueryWrapper);
        List<Long> tags = articleTags.stream().map(articleTag -> articleTag.getTagId()).collect(Collectors.toList());

        ArticleVo articleVo = BeanCopyUtils.copyBean(article,ArticleVo.class);
        articleVo.setTags(tags);
        return articleVo;
    }

    @Override
    public void edit(ArticleDto articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        //更新博客信息
        updateById(article);
        //更新es
        ArticleForEs articleForEs = BeanCopyUtils.copyBean(article, ArticleForEs.class);
        esArticleMapper.update(articleForEs);
    }

    @Override
    public boolean saveViewCountChange(List<Article> articles) {
        articleMapper.updateViewCountChange(articles);
        boolean b = redisCache.deleteObject("article:viewCountChange");
        return b;
    }

    @Override
    public boolean saveLikeCountChange(List<Article> articles) {
        articleMapper.updatelikeCountChange(articles);
        boolean b = redisCache.deleteObject("article:likeCountChange");
        boolean a = redisCache.deleteObject("article:likeCountUserChange");
        return b&&a;
    }

    @Override
    public ResponseResult logicDelete(Long articleId) {
        articleMapper.logicDelete(articleId);
        //删除es
        esArticleMapper.delete(articleId.toString(),ArticleForEs.class);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delete(Long articleId) {
        //删除es数据
        esArticleMapper.delete(articleId.toString(),ArticleForEs.class);
        //删除文章标签关联
        articleTagService.deleteByArticleId(articleId);
        //删除文章
        articleMapper.delete(articleId);
        return ResponseResult.okResult();
    }

}
