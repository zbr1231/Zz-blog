package com.sangeng.mapper;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.ArticleSearchDto;
import com.sangeng.domain.entity.ArticleForEs;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.utils.RedisCache;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EsArticleMapper {
    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Resource
    RedisCache redisCache;
    public void saveBatch(List<ArticleForEs> list){
        elasticsearchRestTemplate.save(list);
    }
    public void delete(String id,Class clazz){
        elasticsearchRestTemplate.delete(id,clazz);
    }
    public void add(ArticleForEs articleForEs){
        elasticsearchRestTemplate.save(articleForEs);
    }
    public ResponseResult highLightText(Integer pageNum,Integer pageSize,String text){
        //高亮对象
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title").field("summary").field("userName")
                .preTags("<span style='background-color:yellow'>")
                .postTags("</span>");
        //构建条件对象
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        //选择性查询
        List<QueryBuilder> should = queryBuilder.should();
        should.add(QueryBuilders.matchQuery("title", text));
        should.add(QueryBuilders.matchQuery("summary", text));
        should.add(QueryBuilders.matchQuery("userName", text));

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                //条件
                .withQuery(queryBuilder)
                //高亮
                .withHighlightBuilder(highlightBuilder)
                //分页
                .withPageable(PageRequest.of(pageNum, pageSize))
                .build();
        //获取查询结果
        SearchHits<ArticleForEs> searchs = elasticsearchRestTemplate.search(query, ArticleForEs.class);
        List<ArticleForEs> list = searchs.stream().map(searchHit -> {
            ArticleForEs articleForEs = searchHit.getContent();
            if (searchHit.getHighlightFields().containsKey("title")) {
                articleForEs.setTitle(searchHit.getHighlightFields().get("title").get(0));
            }
            if (searchHit.getHighlightFields().containsKey("summary")) {
                articleForEs.setSummary(searchHit.getHighlightFields().get("summary").get(0));
            }
            if (searchHit.getHighlightFields().containsKey("userName")) {
                articleForEs.setUserName(searchHit.getHighlightFields().get("userName").get(0));
            }
            return articleForEs;
        }).collect(Collectors.toList());
        list.stream().map(article->{
            Integer viewCount = redisCache.getCacheMapValue("article:viewCount",article.getId()+"");
            if(viewCount == null){
                viewCount = 0;
            }
            article.setViewCount(viewCount.longValue());
            return article;
        }).collect(Collectors.toList());
        PageVo pageVo = new PageVo(list,searchs.getTotalHits());
        return ResponseResult.okResult(pageVo);
    }
    public ResponseResult conditionHighLight(ArticleSearchDto articleSearchDto){
        String title = articleSearchDto.getTitle();
        String summary = articleSearchDto.getSummary();
        String userName = articleSearchDto.getUserName();
        Integer pageNum = articleSearchDto.getPageNum();
        Integer pageSize = articleSearchDto.getPageSize();
        //高亮对象
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title").field("summary").field("userName")
                .preTags("<span style='background-color:yellow'>")
                .postTags("</span>");
        //构建条件对象
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        //一定查询
        List<QueryBuilder> must = queryBuilder.must();
        if(title!=null){
            must.add(QueryBuilders.matchQuery("title", title));
        }
        //选择性查询
        List<QueryBuilder> should = queryBuilder.should();
        if(summary!=null){
            should.add(QueryBuilders.matchQuery("summary", summary));
        }
        if(userName!=null){
            should.add(QueryBuilders.matchQuery("userName", userName));
        }

        NativeSearchQuery query = new NativeSearchQueryBuilder()
                //条件
                .withQuery(queryBuilder)
                //高亮
                .withHighlightBuilder(highlightBuilder)
                //分页
                .withPageable(PageRequest.of(pageNum, pageSize))
                .build();
        //获取查询结果
        SearchHits<ArticleForEs> searchs = elasticsearchRestTemplate.search(query, ArticleForEs.class);
        List<ArticleForEs> list = searchs.stream().map(searchHit -> {
            ArticleForEs articleForEs = searchHit.getContent();
            if (searchHit.getHighlightFields().containsKey("title")) {
                articleForEs.setTitle(searchHit.getHighlightFields().get("title").get(0));
            }
            if (searchHit.getHighlightFields().containsKey("summary")) {
                articleForEs.setSummary(searchHit.getHighlightFields().get("summary").get(0));
            }
            if (searchHit.getHighlightFields().containsKey("userName")) {
                articleForEs.setUserName(searchHit.getHighlightFields().get("userName").get(0));
            }
            return articleForEs;
        }).collect(Collectors.toList());
        list.stream().map(article->{
            Integer viewCount = redisCache.getCacheMapValue("article:viewCount",article.getId()+"");
            if(viewCount == null){
                viewCount = 0;
            }
            article.setViewCount(viewCount.longValue());
            return article;
        }).collect(Collectors.toList());
        PageVo pageVo = new PageVo(list,searchs.getTotalHits());
        return ResponseResult.okResult(pageVo);
    }
    public void update(ArticleForEs articleForEs){
        if(articleForEs==null){
            return;
        }
        String id = articleForEs.getId().toString();
        Document document = getDocument(articleForEs);
        elasticsearchRestTemplate
                .update(UpdateQuery.builder(id).withDocument(document).build(), IndexCoordinates.of("zz-article"));
    }
    public Document getDocument(ArticleForEs articleForEs){
        Document document = Document.create();
        String categoryName = articleForEs.getCategoryName();
        if(categoryName!=null){
            document.put("categoryName",categoryName);
        }
        Date createTime = articleForEs.getCreateTime();
        if(createTime!=null){
            document.put("createTime",createTime);
        }
        String summary = articleForEs.getSummary();
        if(summary!=null){
            document.put("summary",summary);
        }
        String isTop = articleForEs.getIsTop();
        if(isTop!=null){
            document.put("isTop",isTop);
        }
        String thumbnail = articleForEs.getThumbnail();
        if(thumbnail!=null){
            document.put("thumbnail",thumbnail);
        }
        String title = articleForEs.getTitle();
        if(title!=null){
            document.put("title",title);
        }
        Long userId = articleForEs.getUserId();
        if(userId!=null){
            document.put("userId",userId);
        }
        String userName = articleForEs.getUserName();
        if(userName!=null){
            document.put("userName",userName);
        }
        return document;
    }
}
