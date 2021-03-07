package com.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;
import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

/**
 * @author PC
 */
@Data
@TableName(value = "graduation_article")
public class GraduationArticle {
  @TableId(type = AUTO)
  private Long id;
  private String title;
  private String content;
  private String imageUrl;
  private Long createTime;
  private Long viewingTimes;
  private String description;
  private Long userId;
  private String username;

}
