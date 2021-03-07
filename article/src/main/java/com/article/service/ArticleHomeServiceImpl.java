package com.article.service;

import com.article.mapper.GraduationArticleMapper;
import com.article.mapper.GraduationArticleTypeMapper;
import com.article.mapper.GraduationUserCollectMapper;
import com.article.pojo.DailyRecommendation;
import com.article.pojo.HotArticle;
import com.article.pojo.LatestArticle;
import com.article.utils.ArticleCommonUtils;
import com.article.utils.ElasticsearchUtil;
import com.article.utils.EsGraduationProjectArticleFieldInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.pojo.Result;
import com.common.utils.CommonUtils;
import com.common.utils.JwtUtils;
import com.common.utils.constant.ConstantUtils;
import com.controller.controller.article.ArticleHomeControllerInterface;
import com.controller.dto.SelectLatestArticleDTO;
import com.controller.pojo.NameAndValue;
import com.controller.pojo.NameAndValueAndChildren;
import com.controller.pojo.PageInfoResult;
import com.model.entity.GraduationArticle;
import com.model.entity.GraduationArticleType;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.util.Strings;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章服务提供的首页的业务处理信息
 *
 * @author roger
 * @since 2020/12/7 14:33
 */
@Service
public class ArticleHomeServiceImpl implements ArticleHomeControllerInterface {
    @Resource
    private GraduationArticleMapper articleMapper;
    @Resource
    private GraduationArticleTypeMapper articleTypeMapper;

    @Resource
    private GraduationUserCollectMapper graduationUserCollectMapper;
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${ElasticSearch.Index.Article}")
    private String ELASTICSEARCH_INDEX_ARTICLE;


    @Autowired
    private ElasticsearchUtil elasticsearchUtil;

    @Override
    public Result<PageInfoResult<LatestArticle>> searchArticle(String content, Integer pageNum, Integer pageSize, String authentication) {
        Claims claimsFromJwt = JwtUtils.getClaimsFromJwt(jwtSecret, authentication);

        PageInfoResult<LatestArticle> pageInfoResult = new PageInfoResult<>();
        pageInfoResult.setPageNum(pageNum);
        pageInfoResult.setPageSize(pageSize);

        String[] searchIncludeFields = {
                EsGraduationProjectArticleFieldInfo.ID,
                EsGraduationProjectArticleFieldInfo.CREATE_TIME,
                EsGraduationProjectArticleFieldInfo.DESCRIPTION,
                EsGraduationProjectArticleFieldInfo.TITLE,
                EsGraduationProjectArticleFieldInfo.IMAGE_URL,
                EsGraduationProjectArticleFieldInfo.USERNAME
        };
        //相关条件搜索
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        Map<String, Float> fields1 = new HashMap<>(16);
        List<String> filedNames = new ArrayList<>(15);
        fields1.put(EsGraduationProjectArticleFieldInfo.DESCRIPTION, 1.0f);
        fields1.put(EsGraduationProjectArticleFieldInfo.TITLE, 1.0f);
        fields1.put(EsGraduationProjectArticleFieldInfo.USERNAME, 1.0f);
        filedNames.add(EsGraduationProjectArticleFieldInfo.DESCRIPTION);
        filedNames.add(EsGraduationProjectArticleFieldInfo.TITLE);
        filedNames.add(EsGraduationProjectArticleFieldInfo.USERNAME);

        QueryBuilder builder = null;
        if (Strings.isBlank(content)) {
            builder = QueryBuilders.matchAllQuery();
        } else {
            builder = QueryBuilders.multiMatchQuery(content,
                    String.valueOf(filedNames)).fields(fields1).operator(Operator.OR).
                    type(MultiMatchQueryBuilder.Type.BEST_FIELDS).tieBreaker(0.3f);
        }
        boolQueryBuilder.must(builder);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //搜索结果通过from+size进行分页
        searchSourceBuilder.query(boolQueryBuilder)
                .from(pageSize * (pageNum - 1))
                .size(pageSize);
        String[] exclude = {};
        searchSourceBuilder.fetchSource(searchIncludeFields, exclude);
        //得分降序
        searchSourceBuilder.sort(SortBuilders.fieldSort("_score").order(SortOrder.DESC));
        //上传时间倒叙
        searchSourceBuilder.sort(SortBuilders.fieldSort(EsGraduationProjectArticleFieldInfo.CREATE_TIME).order(SortOrder.DESC));

        SearchRequest searchRequest = new SearchRequest(ELASTICSEARCH_INDEX_ARTICLE);
        searchRequest.source(searchSourceBuilder);
        //搜索结果
        List<Map<String, Object>> esDatas = elasticsearchUtil.getEsDatas(searchRequest, pageInfoResult);

        List<LatestArticle> articles = new ArrayList<>(pageSize);

        //记录博客id
        List<Long> blogIds = new ArrayList<>(pageSize);
        for (Map<String, Object> esData : esDatas) {
            blogIds.add(Long.parseLong(esData.get(EsGraduationProjectArticleFieldInfo.ID) + ""));
        }
        //文章其他信息
        Map<String, Map<String, Object>> idToInfo = articleTypeMapper.selectArticlesByLayerAndBlogIds(GraduationArticleType.LAYER_ONE, blogIds);
        //收藏信息
        List<Long> collectedIds = new ArrayList<>(pageSize);
        if (claimsFromJwt != null) {
            collectedIds = graduationUserCollectMapper.selectCollectBlogIdsByBlogIds(blogIds, claimsFromJwt.get(ConstantUtils.JWT_CLAIMS_FIELD_ID, Long.class));
        }
        //处理收藏数据，处理观看次数,类型数据
        for (Map<String, Object> es : esDatas) {
            LatestArticle latestArticle = new LatestArticle();
            latestArticle.setId(Long.parseLong(es.get(EsGraduationProjectArticleFieldInfo.ID) + ""));
            latestArticle.setTime("" + es.get(EsGraduationProjectArticleFieldInfo.CREATE_TIME));
            latestArticle.setDescription("" + es.get(EsGraduationProjectArticleFieldInfo.DESCRIPTION));
            latestArticle.setImageUrl("" + (es.get(EsGraduationProjectArticleFieldInfo.IMAGE_URL)));
            latestArticle.setTitle("" + (es.get(EsGraduationProjectArticleFieldInfo.TITLE)));

            //其他信息
            Map<String, Object> info = idToInfo.get(latestArticle.getId() + "");
            if (info == null) {
                info = new HashMap<>(2);
            }
            //观看次数
            Object viewing_times = info.get("viewing_times");
            if (viewing_times == null) {
                latestArticle.setViewingTimes(0);
            } else {
                latestArticle.setViewingTimes(Integer.parseInt(viewing_times + ""));
            }
            //类型
            Object type = info.get("name");
            if (type == null) {
                latestArticle.setType("");
            } else {
                latestArticle.setType(type + "");
            }

            //是否被收藏
            if (collectedIds.contains(latestArticle.getId())) {
                latestArticle.setIsCollect(LatestArticle.COLLECT);
            } else {
                latestArticle.setIsCollect(LatestArticle.NOT_COLLECT);
            }
            articles.add(latestArticle);
        }
        pageInfoResult.setList(articles);
        return Result.ok(pageInfoResult);
    }

    @Override
    public Result<List<NameAndValue>> selectArticleType(Integer id) {
        List<NameAndValue> nameAndValues = new ArrayList<>(10);
        GraduationArticleType graduationArticleType = new GraduationArticleType();
        graduationArticleType.setPid(id);
        graduationArticleType.setLayer(GraduationArticleType.LAYER_TWO);
        List<GraduationArticleType> graduationArticleTypes = articleTypeMapper.selectList(new QueryWrapper<>(graduationArticleType));
        for (GraduationArticleType articleType : graduationArticleTypes) {
            nameAndValues.add(new NameAndValue(articleType.getName(), articleType.getId()));
        }
        return Result.ok(nameAndValues);
    }

    @Override
    public Result<List<NameAndValue>> selectArticlePrograma() {
        List<NameAndValue> nameAndValues = new ArrayList<>(10);
        GraduationArticleType graduationArticleType = new GraduationArticleType();
        graduationArticleType.setLayer(GraduationArticleType.LAYER_ONE);
        List<GraduationArticleType> graduationArticleTypes = articleTypeMapper.selectList(new QueryWrapper<>(graduationArticleType));
        for (GraduationArticleType articleType : graduationArticleTypes) {
            nameAndValues.add(new NameAndValue(articleType.getName(), articleType.getId()));
        }
        return Result.ok(nameAndValues);
    }

    @Override
    public Result<List<NameAndValueAndChildren>> selectArticleTypePrograma() {
        return Result.ok(articleTypeMapper.selectArticleTypePrograma());
    }

    @Override
    public Result<List<HotArticle>> selectHotArticle() {
        return Result.ok(articleMapper.selectHotArticle());
    }

    @Override
    public Result<PageInfoResult> selectLatestArticle(SelectLatestArticleDTO selectLatestArticleDTO, String authentication) {
        IPage<LatestArticle> page = new Page<>(selectLatestArticleDTO.getPageNum(), selectLatestArticleDTO.getPageSize());
        IPage e = articleMapper.selectLatestArticle(page, selectLatestArticleDTO.getType());
        PageInfoResult<LatestArticle> re = new PageInfoResult<>();
        re.setTotal(e.getTotal());
        re.setList(e.getRecords());
        re.setPageSize(selectLatestArticleDTO.getPageSize());
        re.setPageNum(selectLatestArticleDTO.getPageNum());


        if (re.getList().size() != 0) {
            String type = articleTypeMapper.selectById(selectLatestArticleDTO.getType()).getName();
            List<Long> ids = new ArrayList<>(selectLatestArticleDTO.getPageSize());
            for (LatestArticle latestArticle : re.getList()) {
                ids.add(latestArticle.getId());
            }
            List<Long> collectBlogIds = new ArrayList<>(10);
            if (!Strings.isBlank(authentication)) {
                collectBlogIds = graduationUserCollectMapper.selectCollectBlogIdsByBlogIds(ids, JwtUtils.getUserId(jwtSecret, authentication));
            }
            for (LatestArticle latestArticle : re.getList()) {
                //文章类型
                latestArticle.setType(type);
                //收藏与否
                if (collectBlogIds.contains(latestArticle.getId())) {
                    latestArticle.setIsCollect(LatestArticle.COLLECT);
                } else {
                    latestArticle.setIsCollect(LatestArticle.NOT_COLLECT);
                }
            }
        }
        return Result.ok(re);
    }

    @Override
    public Result selectDailySentence() {
        return Result.ok(articleMapper.selectDailySentence(CommonUtils.getCurrentTimeAs20201010()));
    }

    @Override
    public Result<DailyRecommendation> selectDailyRecommendation() {
        return Result.ok(articleMapper.selectDailyRecommendation(CommonUtils.getCurrentTimeAs20201010()));
    }

}