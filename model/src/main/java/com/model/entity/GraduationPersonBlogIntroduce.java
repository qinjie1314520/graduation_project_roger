package com.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 个人博客——自我介绍
 * @author roger
 * @since  2021/1/23 10:50
 */
@Data
public class GraduationPersonBlogIntroduce {

  private String content;
  @TableId
  private Long userId;
  private String imageUrl;
  private String titleCh;
  private String titleEn;
}
