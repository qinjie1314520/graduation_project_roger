package com.model.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

/**
 * @author PC
 */
@Data
@TableName(value = "graduation_article_comment")
public class GraduationArticleComment {
  @TableId(type = AUTO)
  private Long id;
  private Long createTime;
  private Long articleId;
  private String content;
  private String username;
  private Long userId;
  private String profile;
  private String location;
}
