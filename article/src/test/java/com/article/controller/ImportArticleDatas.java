package com.article.controller;

import Jama.Matrix;
import com.alibaba.fastjson.JSONObject;
import com.article.mapper.GraduationArticleMapper;
import com.article.mapper.GraduationArticleTypeRelationshipMapper;
import com.article.mapper.RecommendNeedDatasMapper;
import com.article.pojo.ScoreInfo;
import com.article.utils.HttpRequest;
import com.article.utils.ThreadPoolExecutorService;
import com.common.utils.CommonUtils;
import com.controller.pojo.NameAndValue;
import com.model.entity.GraduationArticle;
import com.model.entity.GraduationArticleTypeRelationship;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class ImportArticleDatas {
    @Resource
    private GraduationArticleMapper graduationArticleMapper;
    @Resource
    private GraduationArticleTypeRelationshipMapper graduationArticleTypeRelationshipMapper;

    @Autowired
    private ThreadPoolExecutorService threadPoolExecutorService;

    @Test
    void insertArticleAi() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < 35; i++) {
            String html = HttpRequest.sendGetStr("https://ai.csdn.net/?page=" + i);
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

                    Date parse = sdf.parse((time + " 00:00:00").replaceAll("\\.", "-"));
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


        }

    }

    @Test
    void insertArticleRecommend() throws Exception {
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

//        for (int i = 0; i < 5; i++) {
        threadPoolExecutorService.add(() -> {
            for (String key : keyToStr.keySet()) {
                JSONObject jsonObject = HttpRequest.sendGet("https://www.csdn.net/api/articles?type=new&category=" + key);
                List<JSONObject> articles = JSONObject.parseArray(jsonObject.get("articles") + "", JSONObject.class);
                List<GraduationArticle> graduationArticles = new ArrayList<>(100);
                List<GraduationArticleTypeRelationship> graduationArticleTypeRelationships = new ArrayList<>(100);
                for (JSONObject article : articles) {
                    GraduationArticle graduationArticle = new GraduationArticle();
                    graduationArticle.setTitle(article.getString("title"));
                    String detailHtml = HttpRequest.sendGetStr(article.getString("url"));
                    if (!Strings.isBlank(detailHtml)) {
                        Document details = Jsoup.parse(detailHtml);
                        Element ee = details.getElementsByClass("baidu_pl").get(0);
                        Elements imgs = ee.getElementsByTag("img");
                        for (int i = 0; i < imgs.size(); i++) {
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
        });
//        }
    }


    @Test
    void test() throws Exception {
        String detailHtml = HttpRequest.sendGetStr("https://blog.csdn.net/qq_49290616/article/details/114187401");
        if (!Strings.isBlank(detailHtml)) {
            Document details = Jsoup.parse(detailHtml);
            Element ee = details.getElementsByClass("baidu_pl").get(0);
            Elements imgs = ee.getElementsByTag("img");
            for (int i = 0; i < imgs.size(); i++) {
                String src = imgs.attr("src");
                if (!Strings.isBlank(src)) {

                    break;
                }
            }
        }
    }

    @Resource
    RecommendNeedDatasMapper recommendNeedDatasMapper;

    @Test
    void test1() throws Exception {
        Long userId = 1L;
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
                if(score[i][j]!=0){
                    array[i][j] = 0;
                }
            }
        }
        times = new Matrix(array);

//        times.print(blogIds.size(),2);
        //矩阵转置后得便于得到用户的推荐列表
        Matrix transpose = times.transpose();
        //指定用户id的推荐列表
        double recommendIdList[] = transpose.getArray()[userIdToI.get(userId)];
        List<NameAndValue> blogIdToRecommend = new ArrayList<>(recommendIdList.length);
        for (int i = 0; i < recommendIdList.length; i++) {
            blogIdToRecommend.add(new NameAndValue(blogIds.get(i) + "", recommendIdList[i]));
        }
        List<Long> revommendBlogIds = blogIdToRecommend.stream()
                .sorted((o1, o2) -> Double.compare(Double.parseDouble(o2.getValue() + ""), Double.parseDouble(o1.getValue() + "")))
                .limit(5).map(x->Long.parseLong(x.getName())).collect(Collectors.toList());
    }
}
