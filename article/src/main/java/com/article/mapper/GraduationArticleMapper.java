package com.article.mapper;

import com.article.pojo.DailyRecommendation;
import com.article.pojo.DailySentence;
import com.article.pojo.HotArticle;
import com.article.pojo.LatestArticle;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.controller.pojo.NameAndValue;
import com.model.entity.GraduationArticle;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 博客数据管理
 * @author roger
 * @since  2021/1/24 19:08
 */
public interface GraduationArticleMapper extends BaseMapper<GraduationArticle> {
    /**
      * 查询文章类型的name和value
      * @param layer 层级
      * @return java.util.List
      */
    @Select("select id as value,name from graduation_article_type where layer = #{layer} ")
    List<NameAndValue> selectArticleTypeNameAndValueByLayer(Integer layer);
    /**
     * 热门文章，依据围观次数判断，先查询最热的5个
     * @return java.util.List
     */
    @Select("select id,title, image_url as imageUrl,create_time as time,viewing_times as viewingTimes from graduation_article order by viewing_times desc limit 0,5")
    List<HotArticle> selectHotArticle();

    @Select("select id,title, image_url as imageUrl,create_time as time,viewing_times as viewingTimes,description from graduation_article,graduation_article_type_relationship where graduation_article.id = graduation_article_type_relationship.article_id and graduation_article_type_relationship.article_type_id = #{type} order by create_time desc")
    IPage<LatestArticle> selectLatestArticle(IPage<LatestArticle> page, @Param("type") Integer type);
    /**
     * 查询每日一句
     * @param time 展示时间，202001001
     * @return com.article.pojo.DailySentence
     */
    @Select("select create_time,content from graduation_daily_sentence where show_day = #{time} order by create_time  desc limit 1")
    DailySentence selectDailySentence(String time);
    /**
     * 查询每日一条推荐
     * @param time 20201010
     * @return com.article.pojo.DailyRecommendation
     */
    @Select("select graduation_article.id,graduation_article.title,graduation_article.description \n" +
            "from graduation_article,graduation_daily_recommendation where graduation_daily_recommendation.show_day=#{time} and graduation_article.id = graduation_daily_recommendation.article_id limit 1")
    DailyRecommendation selectDailyRecommendation(String time);

    /**
     * 更新阅读次数
     * @param id 博客id
     */
    @Update("update graduation_article set viewing_times = viewing_times + 1 where id = #{id}")
    void incrementViewTimes(Long id);


    void insertList(List<GraduationArticle> list);


}
