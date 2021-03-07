package com.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.model.entity.GraduationArticle;
import com.model.entity.GraduationArticleTypeRelationship;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface GraduationArticleTypeRelationshipMapper extends BaseMapper<GraduationArticleTypeRelationship> {
    /**
      * 插入更新文章类型栏目
      * @param
      * @return
      */
    void replaceIntoArticleProgramaType(List<GraduationArticleTypeRelationship> graduationArticleTypeRelationships);
}
