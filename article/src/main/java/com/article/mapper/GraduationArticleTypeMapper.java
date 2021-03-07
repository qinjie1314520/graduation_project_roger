package com.article.mapper;

import com.article.pojo.DailyRecommendation;
import com.article.pojo.DailySentence;
import com.article.pojo.HotArticle;
import com.article.pojo.LatestArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.controller.pojo.NameAndValue;
import com.controller.pojo.NameAndValueAndChildren;
import com.model.entity.GraduationArticle;
import com.model.entity.GraduationArticleType;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 博客数据管理
 * @author roger
 * @since  2021/1/24 19:08
 */
public interface GraduationArticleTypeMapper extends BaseMapper<GraduationArticleType> {
    @Select("select graduation_article_type.* from graduation_article_type,graduation_article_type_relationship\n" +
            "where graduation_article_type.id = graduation_article_type_relationship.article_type_id\n" +
            "and article_id = #{id} and status = 1")
    List<GraduationArticleType> selectGraduationArticleTypeByArticleId(Long id);
    @Select("select graduation_article_type.* from graduation_article_type,graduation_article_type_relationship\n" +
            "where graduation_article_type.id = graduation_article_type_relationship.article_type_id\n" +
            "and article_id = #{id} and layer=#{layer} and status = 1")
    List<GraduationArticleType> selectGraduationArticleTypeByArticleIdAndLayer(@Param("id") Long id,
                                                                               @Param("layer") Integer layer);

    List<NameAndValueAndChildren> selectArticleTypePrograma();
    /**
     * 通过文章ids查询文章的一些信息，映射成 id - 》 {id:?,?:?}.这里主要查询类型和浏览量
     * @param layer 类型的层级
     * @param ids 博客id
     * @return java.util.Map
     */
    @MapKey("id")
    Map<String,Map<String, Object>> selectArticlesByLayerAndBlogIds(@Param("layer") Integer layer,@Param("ids") List<Long> ids);
}