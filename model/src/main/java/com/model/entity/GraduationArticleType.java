package com.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

@Data
@TableName(value = "graduation_article_type")
public class GraduationArticleType {
  /**
   * 第一层
   */
  public static final int LAYER_ONE = 1;
  /**
   * 第二层
   */
  public static final int LAYER_TWO = 2;
  @TableId(type = AUTO)
  private Long id;
  private String name;
  private Integer pid;
  private Long createTime;
  private Integer status;
  private Integer layer;


}
