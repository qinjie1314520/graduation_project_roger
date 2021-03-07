package com.article.pojo;

import com.controller.pojo.NameAndValue;
import com.controller.pojo.PageInfoResult;
import com.model.entity.GraduationArticle;
import com.model.entity.GraduationArticleComment;
import lombok.Data;

import java.util.List;

/**
 * 博客详情
 * @author roger
 * @since  2021/2/20 17:29
 */
@Data
public class BlogDetails {
    private GraduationArticle article;
    private PageInfoResult<GraduationArticleComment> comments;
    private List<NameAndValue> oneLabels;
    private List<NameAndValue> twoLabels;
}
