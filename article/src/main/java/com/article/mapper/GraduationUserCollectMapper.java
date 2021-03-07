package com.article.mapper;

import com.article.pojo.DailyRecommendation;
import com.article.pojo.DailySentence;
import com.article.pojo.HotArticle;
import com.article.pojo.LatestArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.controller.pojo.NameAndValue;
import com.model.entity.GraduationArticle;
import com.model.entity.GraduationUserCollect;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户收藏
 * @author roger
 * @since  2021/1/24 19:08
 */
public interface GraduationUserCollectMapper extends BaseMapper<GraduationUserCollect> {
    /**
      * 查询收藏列表
      * @param userId 用户id
      * @param page 分页信息
      * @return com.baomidou.mybatisplus.core.metadata.IPage
      */
    @Select("select graduation_article.id,title, image_url as imageUrl,graduation_article.create_time as time,viewing_times as viewingTimes,description,graduation_article_type.`name` as type from graduation_user_collect LEFT JOIN graduation_article ON (graduation_article.id = graduation_user_collect.blog_id and graduation_user_collect.user_id =#{userId}) left join graduation_article_type_relationship on(graduation_article.id = graduation_article_type_relationship.article_id) left join graduation_article_type on(graduation_article_type_relationship.article_type_id = graduation_article_type.id) where graduation_article_type.layer =1 order by graduation_user_collect.create_time desc ")
    IPage<LatestArticle> selectUserCollectBlogs(IPage<LatestArticle> page, @Param("userId") Long userId);

    /**
      * 列表数据处理——查询博客列表的数据是否被当前用户收藏，如果用户登录了
      * @param ids 博客ids
      * @param userId 用户id
      * @return java.util.List
      */
    List<Long> selectCollectBlogIdsByBlogIds(@Param("ids") List<Long> ids, @Param("userId") Long userId);

}
