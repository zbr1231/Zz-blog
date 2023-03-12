package com.sangeng;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.*;
import com.sangeng.domain.vo.HotArticleVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.exception.SystemException;
import com.sangeng.mapper.*;
import com.sangeng.service.ArticleService;
import com.sangeng.service.CategoryService;
import com.sangeng.service.RecommendService;
import com.sangeng.service.UserService;
import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.RedisCache;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.BulkOptions;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = SanGengBlogApplication.class)
@Component
@ConfigurationProperties(prefix = "oss")
public class OSSTest {

    private String accessKey;
    private String secretKey;
    private String bucket;
    @Resource
    ArticleMapper articleMapper;

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    @Test
    public void testOss(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
//        String accessKey = "your access key";
//        String secretKey = "your secret key";
//        String bucket = "sg-blog";

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "2022/sg.png";

        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);


            InputStream inputStream = new FileInputStream("C:\\Users\\root\\Desktop\\Snipaste_2022-02-28_22-48-37.png");
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }

    }
    @Resource
    RecommendService recommendService;
    @Resource
    RedisCache redisCache;
    @Resource
    UserService userService;
    @Resource
    CategoryService categoryService;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Resource
    EsArticleMapper esArticleMapper;
    @Test
    public void test2(){

//        BulkOptions.BulkOptionsBuilder builder = BulkOptions.builder();
////设置刷新策略
//        builder.withRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
////1、批量更新
//        elasticsearchRestTemplate.bulkUpdate(updateQueries, builder.build(), IndexCoordinates.of("索引名称"));

        List<Article> articles = articleMapper.selectList(null);
        articles.stream()
                .map(article->{
                    article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
                    article.setUserName(userService.getById(article.getUserId()).getNickName());
                    return article;
                })
                .collect(Collectors.toList());
        List<ArticleForEs> articleForEs = BeanCopyUtils.copyBeanList(articles, ArticleForEs.class);
        System.out.println(articleForEs.size());
//        elasticsearchRestTemplate.createIndex(ArticleForEs.class);
//        elasticsearchRestTemplate.putMapping(ArticleForEs.class);
        elasticsearchRestTemplate.save(articleForEs);

    }
    @Test
    public void test3(){
        String text = "java";
        //高亮对象
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title").field("summary").field("userName")
                .preTags("<span style='background-color:yellow'>")
                .postTags("</span>");
        //构建条件对象
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        //一定查询
//        List<QueryBuilder> must = queryBuilder.must();
//        must.add(QueryBuilders.matchQuery("title", text));
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
                .withPageable(PageRequest.of(0, 4))
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
        System.out.println(list);
        //判断是否还有多余的数据
        if (list.size() > (0 + 1) * 2) {
            System.out.println(true);
        }

    }
    @Test
    public void test4(){
        Document document = Document.create();
        document.put("summary","64");
        elasticsearchRestTemplate.update(UpdateQuery.builder("64").withDocument(document).build(),IndexCoordinates.of("zz-article"));
    }
    @Resource
    UserMapper userMapper;
    @Test
    public void test5(){

        Page<User> page = new Page<>();
        page.setOptimizeCountSql(true);
        page.setSearchCount(false);
        page.setCurrent(0);
        page.setSize(5);
        Page<User> userPage = userMapper.selectPage(page, null);
        List<User> records = userPage.getRecords();
        System.out.println(page.getTotal());

    }
}
