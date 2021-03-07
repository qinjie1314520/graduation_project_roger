package com.article.service;

import Jama.Matrix;
import com.alibaba.fastjson.JSONObject;
import com.article.mapper.*;
import com.article.openfeign.UserCenterFeignService;
import com.article.pojo.BlogDetails;
import com.article.pojo.LatestArticle;
import com.article.pojo.ScoreInfo;
import com.article.utils.HttpRequest;
import com.article.utils.ThreadPoolExecutorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.pojo.ExceptionEnum;
import com.common.pojo.RRException;
import com.common.pojo.Result;
import com.common.utils.JwtUtils;
import com.common.utils.constant.ConstantUtils;
import com.controller.controller.article.PersonBlogControllerInterface;
import com.controller.dto.article.*;
import com.controller.pojo.NameAndValue;
import com.controller.pojo.PageInfoResult;
import com.model.entity.*;
import com.model.pojo.User;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 个人博客——博客管理
 *
 * @author roger
 * @since 2020/12/7 14:33
 */
@Service
public class PersonBlogServiceImpl implements PersonBlogControllerInterface {

    @Resource
    GraduationArticleMapper graduationArticleMapper;
    @Resource
    GraduationArticleCommentMapper graduationArticleCommentMapper;
    @Resource
    GraduationArticleTypeMapper graduationArticleTypeMapper;
    @Resource
    GraduationUserBlogHistoryMapper graduationUserBlogHistoryMapper;
    @Autowired
    private UserCenterFeignService userCenterFeignService;
    @Autowired
    private ThreadPoolExecutorService threadPoolExecutorService;
    @Resource
    private RecommendNeedDatasMapper recommendNeedDatasMapper;

    @Resource
    GraduationArticleTypeRelationshipMapper graduationArticleTypeRelationshipMapper;
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public Result selPersonBlogList(SelPersonBlogListDto selPersonBlogListDto, String authentication) {
        IPage<GraduationArticle> page = new Page<>(selPersonBlogListDto.getPageNum(), selPersonBlogListDto.getPageSize());
        GraduationArticle g = new GraduationArticle();
        g.setUserId(selPersonBlogListDto.getId());
        IPage e = graduationArticleMapper.selectPage(page, new QueryWrapper<>(g).orderByDesc("create_time"));
        PageInfoResult<LatestArticle> re = new PageInfoResult<>();
        re.setTotal(e.getTotal());
        re.setList(e.getRecords());
        re.setPageSize(selPersonBlogListDto.getPageSize());
        re.setPageNum(selPersonBlogListDto.getPageNum());
        return Result.ok(re);
    }

    @Override
    public Result delPersonBlog(DelPersonBlogDto delPersonBlogDto, String authentication) {

        GraduationArticle g = new GraduationArticle();
        g.setId(delPersonBlogDto.getId());
        g.setUserId(JwtUtils.getUserId(jwtSecret, authentication));
        graduationArticleMapper.delete(new UpdateWrapper<>(g));

        GraduationArticleTypeRelationship graduationArticleTypeRelationship = new GraduationArticleTypeRelationship();
        graduationArticleTypeRelationship.setArticleId(delPersonBlogDto.getId());

        graduationArticleTypeRelationshipMapper.delete(new QueryWrapper<>(graduationArticleTypeRelationship));
        return Result.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insPersonBlog(InsPersonBlogDto insPersonBlogDto, String authentication) {
        Claims claimsFromJwt = JwtUtils.getClaimsFromJwt(jwtSecret, authentication);
        String username = null;
        Long userId = null;
        if (claimsFromJwt != null) {
            userId = claimsFromJwt.get(ConstantUtils.JWT_CLAIMS_FIELD_ID,Long.class);
            username = claimsFromJwt.get(ConstantUtils.JWT_CLAIMS_FIELD_USERNAME,String.class);
        }

        GraduationArticle graduationArticle = new GraduationArticle();
        graduationArticle.setUserId(userId);
        graduationArticle.setUsername(username);
        graduationArticle.setContent(insPersonBlogDto.getContent());
        graduationArticle.setCreateTime(System.currentTimeMillis());
        graduationArticle.setImageUrl(insPersonBlogDto.getCoverImage());
        graduationArticle.setDescription(insPersonBlogDto.getDescription());
        graduationArticle.setTitle(insPersonBlogDto.getTitle());
        graduationArticle.setViewingTimes(0L);
        graduationArticleMapper.insert(graduationArticle);


        insPersonBlogDto.getType().add(insPersonBlogDto.getPrograma());
        //插入的文章类型数据
        List<GraduationArticleTypeRelationship> graduationArticleTypeRelationships = new ArrayList<>(insPersonBlogDto.getType().size());
        for (Long aLong : insPersonBlogDto.getType()) {
            GraduationArticleTypeRelationship graduationArticleTypeRelationship = new GraduationArticleTypeRelationship();
            graduationArticleTypeRelationship.setArticleId(graduationArticle.getId());
            graduationArticleTypeRelationship.setArticleTypeId(aLong);
            graduationArticleTypeRelationships.add(graduationArticleTypeRelationship);
        }
        graduationArticleTypeRelationshipMapper.replaceIntoArticleProgramaType(graduationArticleTypeRelationships);

        return Result.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updPersonBlog(UpdPersonBlogDto updPersonBlogDto, String authentication) {
        GraduationArticle graduationArticle = new GraduationArticle();
        graduationArticle.setUserId(JwtUtils.getUserId(jwtSecret, authentication));
        graduationArticle.setContent(updPersonBlogDto.getContent());
        graduationArticle.setDescription(updPersonBlogDto.getDescription());
        graduationArticle.setTitle(updPersonBlogDto.getTitle());
        graduationArticle.setImageUrl(updPersonBlogDto.getCoverImage());
        graduationArticle.setId(updPersonBlogDto.getId());
        graduationArticleMapper.updateById(graduationArticle);

        updPersonBlogDto.getType().add(updPersonBlogDto.getPrograma());
        List<GraduationArticleTypeRelationship> list = new ArrayList<>(updPersonBlogDto.getType().size());
        for (Long aLong : updPersonBlogDto.getType()) {
            GraduationArticleTypeRelationship graduationArticleTypeRelationship = new GraduationArticleTypeRelationship();
            graduationArticleTypeRelationship.setArticleTypeId(aLong);
            graduationArticleTypeRelationship.setArticleId(updPersonBlogDto.getId());
            list.add(graduationArticleTypeRelationship);
        }
        if (list.size() != 0) {
            graduationArticleTypeRelationshipMapper.replaceIntoArticleProgramaType(list);
        }
        return Result.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result personBlogDetails(Long id, String authentication) {

        //内容
        GraduationArticle graduationArticle = graduationArticleMapper.selectById(id);
        BlogDetails blogDetails = new BlogDetails();
        blogDetails.setArticle(graduationArticle);
        List<NameAndValue> oneLayerLabel = new ArrayList<>(5);
        List<NameAndValue> twoLayerLabel = new ArrayList<>(5);
        //标签，第一层
        List<GraduationArticleType> graduationArticleTypes = graduationArticleTypeMapper.selectGraduationArticleTypeByArticleId(id);
        for (GraduationArticleType graduationArticleType : graduationArticleTypes) {
            if (GraduationArticleType.LAYER_ONE == graduationArticleType.getLayer()) {
                oneLayerLabel.add(new NameAndValue(graduationArticleType.getName(), graduationArticleType.getId()));
            } else if (GraduationArticleType.LAYER_TWO == graduationArticleType.getLayer()) {
                twoLayerLabel.add(new NameAndValue(graduationArticleType.getName(), graduationArticleType.getId()));
            }
        }
        blogDetails.setOneLabels(oneLayerLabel);
        blogDetails.setTwoLabels(twoLayerLabel);


        //新增浏览记录
        if (!Strings.isBlank(authentication)) {
            GraduationUserBlogHistory graduationUserBlogHistory = new GraduationUserBlogHistory();
            Claims claimsFromJwt = JwtUtils.getClaimsFromJwt(jwtSecret, authentication);
            graduationUserBlogHistory.setUserId(claimsFromJwt.get(ConstantUtils.JWT_CLAIMS_FIELD_ID, Long.class));
            graduationUserBlogHistory.setUsername(claimsFromJwt.get(ConstantUtils.JWT_CLAIMS_FIELD_USERNAME, String.class));
            graduationUserBlogHistory.setBlogId(id);
            graduationUserBlogHistory.setCreateTime(System.currentTimeMillis());
            graduationUserBlogHistoryMapper.insert(graduationUserBlogHistory);
        }
        //更新阅读次数
        graduationArticleMapper.incrementViewTimes(id);
        return Result.ok(blogDetails);
    }

    @Override
    public Result personBlogDetailsComments(Long id, Integer pageNum, Integer pageSize) {
        //评论
        IPage<GraduationArticleComment> page = new Page<>(pageNum, pageSize);
        GraduationArticleComment graduationArticleComment = new GraduationArticleComment();
        graduationArticleComment.setArticleId(id);
        IPage e = graduationArticleCommentMapper.selectPage(page, new QueryWrapper<>(graduationArticleComment).orderByDesc("create_time"));
        PageInfoResult<GraduationArticleComment> pageInfoResult = new PageInfoResult<>();
        pageInfoResult.setPageNum(pageNum);
        pageInfoResult.setPageSize(pageSize);
        pageInfoResult.setTotal(e.getTotal());
        pageInfoResult.setList(e.getRecords());
        return Result.ok(pageInfoResult);
    }

    @Override
    public Result personBlogDetailsRecommend(String authentication) {
        List<NameAndValue> nameAndValues = new ArrayList<>(10);
        //权重占比
        //详情浏览： 2分
        //收藏： 5分
        //搜索： 1分
        Long userId = JwtUtils.getUserId(jwtSecret, authentication);
        if (userId == null) {
            return Result.ok(nameAndValues);
        }
        List<ScoreInfo> scoreInfos = recommendNeedDatasMapper.selectScoreInfo();
        Map<String, Integer> userBlogScore = new HashMap<>(scoreInfos.size());
        for (ScoreInfo scoreInfo : scoreInfos) {
            userBlogScore.put(scoreInfo.getBlogId() + ":" + scoreInfo.getUserId(), scoreInfo.getScore());
        }
        //矩阵中有哪些用户和博客
        List<Long> userIds = recommendNeedDatasMapper.selectMatrixUserIdsOrderByUserIdsAsc();
        List<Long> blogIds = recommendNeedDatasMapper.selectMatrixBlogIdsOrderByBlogIdsAsc();

        Map<Long, Integer> userIdToI = new HashMap<>(userIds.size());
        for (int i = 0; i < userIds.size(); i++) {
            userIdToI.put(userIds.get(i), i);
        }

        double score[][] = new double[blogIds.size()][userIds.size()];
        for (int i = 0; i < blogIds.size(); i++) {
            for (int j = 0; j < userIds.size(); j++) {
                Integer integer = userBlogScore.get(blogIds.get(i) + ":" + userIds.get(j));
                score[i][j] = integer == null ? 0.0 : integer.doubleValue();
            }
        }
        //行——用户id，
        //列——博客id
        //用户博客评分矩阵
        Matrix matrixUserToBlog = new Matrix(score);
//        matrixUserToBlog.print(blogIds.size(),0);


        //保存物品相似度
        Map<String, Double> blogSimilarityMap = new HashMap<>(blogIds.size() * blogIds.size());
        //根据余弦相似度计算博客之间的相似度
        for (int i = 0; i < blogIds.size(); i++) {
            for (int j = i; j < blogIds.size(); j++) {
                Double similarity = 0.0;
                if (i == j) {
                    similarity = 1.00;
                } else {
                    //Numerator and Denominator
                    //参考网址https://blog.csdn.net/xj6591073/article/details/79049469
                    //分子计算
                    double numerator = 0;
                    //分母计算
                    double denominator = 0;
                    double num1 = 0;
                    double num2 = 0;
                    for (int k = 0; k < userIds.size(); k++) {
                        numerator += score[i][k] * score[j][k];
                        num1 += score[i][k] * score[i][k];
                        num2 += score[j][k] * score[j][k];
                    }
                    denominator = Math.sqrt(num1) + Math.sqrt(num2);
                    if (denominator == 0) {
                        denominator = 1;
                    }
                    similarity = numerator / denominator;
                }
                blogSimilarityMap.put(i + ":" + j, similarity);
                blogSimilarityMap.put(j + ":" + i, similarity);
            }
        }

        //构建博客博客的相似度矩阵
        double blogSimilarity[][] = new double[blogIds.size()][blogIds.size()];
        for (int i = 0; i < blogIds.size(); i++) {
            for (int j = 0; j < blogIds.size(); j++) {
                Double aDouble = blogSimilarityMap.get(i + ":" + j);
                blogSimilarity[i][j] = aDouble == null ? 0.0 : aDouble;
            }
        }
        Matrix matrixBlogToBlog = new Matrix(blogSimilarity);
//        matrixBlogToBlog.print(blogIds.size(),2);

        //用户博客评分矩阵*博客博客相似度矩阵
        //得到推荐列表
        Matrix times = matrixBlogToBlog.times(matrixUserToBlog);
        //推荐过的进行置0
        double[][] array = times.getArray();
        for (int i = 0; i < blogIds.size(); i++) {
            for (int j = 0; j < userIds.size(); j++) {
                if (score[i][j] != 0) {
                    array[i][j] = 0;
                }
            }
        }
        times = new Matrix(array);

//        times.print(blogIds.size(),2);
        //矩阵转置后得便于得到用户的推荐列表
        Matrix transpose = times.transpose();
        //指定用户id的推荐列表
        Integer integer = userIdToI.get(userId);
        if (integer == null) {
            //标识这个用户还没有任何记录
            //直接返回空数组
            return Result.ok(nameAndValues);
        }
        double recommendIdList[] = transpose.getArray()[userIdToI.get(userId)];
        List<NameAndValue> blogIdToRecommend = new ArrayList<>(recommendIdList.length);
        for (int i = 0; i < recommendIdList.length; i++) {
            blogIdToRecommend.add(new NameAndValue(blogIds.get(i) + "", recommendIdList[i]));
        }
        List<Long> revommendBlogIds = blogIdToRecommend.stream()
                .sorted((o1, o2) -> Double.compare(Double.parseDouble(o2.getValue() + ""), Double.parseDouble(o1.getValue() + "")))
                .limit(5).map(x -> Long.parseLong(x.getName())).collect(Collectors.toList());

        List<GraduationArticle> graduationArticles = graduationArticleMapper.selectBatchIds(revommendBlogIds);
        for (GraduationArticle graduationArticle : graduationArticles) {
            nameAndValues.add(new NameAndValue(graduationArticle.getTitle(), graduationArticle.getId()));
        }
        return Result.ok(nameAndValues);
    }

    @Override
    public Result insertBlogComment(InsertBlogCommentDto insertBlogCommentDto, String authentication) {
        GraduationArticleComment graduationArticleComment = new GraduationArticleComment();
        Long userId = JwtUtils.getUserId(jwtSecret, authentication);
        graduationArticleComment.setUserId(userId);
        graduationArticleComment.setArticleId(insertBlogCommentDto.getArticleId());
        graduationArticleComment.setContent(insertBlogCommentDto.getContent());
        graduationArticleComment.setCreateTime(System.currentTimeMillis());

        Result<User> result = userCenterFeignService.selUserInfo(authentication);
        if (result.getCode() == ExceptionEnum._200.getCode() && result.getDatas() != null) {
            User user = result.getDatas();
            //地域
            graduationArticleComment.setLocation(insertBlogCommentDto.getLocation());
            //头像
            graduationArticleComment.setProfile(user.getProfile());
            //评论用户id
            graduationArticleComment.setUsername(user.getName());
            graduationArticleCommentMapper.insert(graduationArticleComment);
            return Result.ok();
        } else {
            throw new RRException("用户异常，请联系管理员");
        }
    }


    public Result insertArticleRecommend() throws IOException {
        Map<String, Integer> keyToStr = new HashMap<>();
        keyToStr.put("java", 2);
        keyToStr.put("python", 3);
        keyToStr.put("arch", 4);
        keyToStr.put("blockchain", 5);
        keyToStr.put("db", 6);
        keyToStr.put("game", 7);
        keyToStr.put("mobile", 8);
        keyToStr.put("5g", 9);
        keyToStr.put("career", 10);
        keyToStr.put("ops", 11);
        keyToStr.put("sec", 12);
        keyToStr.put("iot", 13);
        keyToStr.put("engineering", 14);
        keyToStr.put("javascript", 15);
        keyToStr.put("web", 16);
        keyToStr.put("fund", 17);
        keyToStr.put("avi", 18);
        keyToStr.put("other", 19);

        for (String key : keyToStr.keySet()) {
            for (int i = 0; i < 4; i++) {
                JSONObject jsonObject = HttpRequest.sendGet("https://www.csdn.net/api/articles?type=new&category=" + key);
                List<JSONObject> articles = JSONObject.parseArray(jsonObject.get("articles") + "", JSONObject.class);
                List<GraduationArticle> graduationArticles = new ArrayList<>(100);
                List<GraduationArticleTypeRelationship> graduationArticleTypeRelationships = new ArrayList<>(100);
                for (JSONObject article : articles) {
                    GraduationArticle graduationArticle = new GraduationArticle();
                    graduationArticle.setTitle(article.getString("title"));

                    GraduationArticle g = new GraduationArticle();
                    g.setTitle(graduationArticle.getTitle());
                    if (null == graduationArticleMapper.selectOne(new QueryWrapper<>(g))) {
                        String detailHtml = HttpRequest.sendGetStr(article.getString("url"));
                        if (!Strings.isBlank(detailHtml)) {
                            Document details = Jsoup.parse(detailHtml);
                            Element ee = details.getElementsByClass("baidu_pl").get(0);
                            Elements imgs = ee.getElementsByTag("img");
                            for (int ii = 0; ii < imgs.size(); ii++) {
                                String src = imgs.attr("src");
                                if (!Strings.isBlank(src)) {
                                    graduationArticle.setImageUrl(src);
                                    break;
                                }
                            }
                            if (ee != null) {
                                graduationArticle.setContent(ee.html());
                            }
                        }

                        graduationArticle.setUsername(article.getString("nickname"));
                        graduationArticle.setDescription(article.getString("desc"));
                        graduationArticle.setCreateTime(Long.parseLong(article.getString("shown_time")) * 1000);
                        graduationArticle.setImageUrl(article.getString("avatar"));
                        graduationArticle.setViewingTimes(article.getLong("views"));
                        graduationArticle.setUserId(1L);
                        graduationArticles.add(graduationArticle);
                        graduationArticleMapper.insert(graduationArticle);

                        GraduationArticleTypeRelationship graduationArticleTypeRelationship = new GraduationArticleTypeRelationship();
                        graduationArticleTypeRelationship.setArticleTypeId(keyToStr.get(key).longValue());
                        graduationArticleTypeRelationship.setArticleId(graduationArticle.getId());
                        graduationArticleTypeRelationships.add(graduationArticleTypeRelationship);

                        graduationArticleTypeRelationshipMapper.insert(graduationArticleTypeRelationship);
                    }
                }
            }
        }
        return Result.ok();
    }

    public void insertArticleAi() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 35; i++) {
            int finalI = i;
            threadPoolExecutorService.add(() -> {
                String html = HttpRequest.sendGetStr("https://ai.csdn.net/?page=" + finalI);
                Document doc = Jsoup.parse(html);
                Elements e = doc.getElementsByClass("news-ul feedlist_mod");
                if (e != null) {
                    Element element = e.get(0);
                    Elements lis = element.getElementsByTag("li");
                    for (Element li : lis) {
                        Element newsL = li.getElementsByClass("newsL").get(0);
                        Element a = newsL.getElementsByTag("a").get(0);
                        Element img = newsL.getElementsByTag("img").get(0);
                        String detailsUrl = a.attr("href");
                        String coverImg = img.attr("src");

                        Element newsR = li.getElementsByClass("newsR").get(0);
                        String title = newsR.getElementsByClass("news-tit").get(0).getElementsByTag("a").get(0).text();
                        Element descEle = newsR.getElementsByClass("news-text").get(0);
                        String desc = descEle.text();
                        Element newsLeft = li.getElementsByClass("news-left").get(0);
                        Elements a1 = newsLeft.getElementsByTag("a");
                        List<String> type = new ArrayList<>();
                        if (a1 != null) {
                            for (Element element1 : a1) {
                                type.add(element1.text());
                            }
                        }
                        Element timeEle = li.getElementsByClass("time").get(0);
                        String time = timeEle.text();


                        //构建数据库文章数据
                        GraduationArticle graduationArticle = new GraduationArticle();
                        graduationArticle.setUserId(0L);
                        graduationArticle.setViewingTimes(0L);
                        graduationArticle.setImageUrl(coverImg);
                        graduationArticle.setTitle(title);
                        graduationArticle.setUsername("AI科技大本营");

                        Date parse = null;
                        try {
                            parse = sdf.parse((time + " 00:00:00").replaceAll("\\.", "-"));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        graduationArticle.setCreateTime(parse.getTime());
                        graduationArticle.setDescription(desc);

                        String detailsHtml = HttpRequest.sendGetStr(detailsUrl);
                        if (!Strings.isBlank(detailsHtml)) {
                            Document details = Jsoup.parse(detailsHtml);
                            Element ee = details.getElementsByClass("baidu_pl").get(0);
                            if (ee != null) {
                                graduationArticle.setContent(ee.html());
                                graduationArticleMapper.insert(graduationArticle);
                                GraduationArticleTypeRelationship graduationArticleTypeRelationship = new GraduationArticleTypeRelationship();
                                graduationArticleTypeRelationship.setArticleTypeId(1L);
                                graduationArticleTypeRelationship.setArticleId(graduationArticle.getId());
                                graduationArticleTypeRelationshipMapper.insert(graduationArticleTypeRelationship);

                            }
                        }
                    }
                }
            });
        }
    }
}

