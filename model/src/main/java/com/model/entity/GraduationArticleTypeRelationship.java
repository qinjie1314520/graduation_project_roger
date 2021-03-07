package com.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "graduation_article_type_relationship")
@Data
public class GraduationArticleTypeRelationship {
  private Long articleId;
  private Long articleTypeId;


}
