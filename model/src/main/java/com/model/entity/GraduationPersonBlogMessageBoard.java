package com.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;
import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

/**
 * 个人博客——留言板
 * @author roger
 * @since  2021/1/23 10:51
 */
@Data
public class GraduationPersonBlogMessageBoard {
  @TableId(type = AUTO)
  private Long id;
  private Long createTime;
  private String content;
  private Long userId;
  private Long sourceId;
  private String sourceName;
  private String sourceProfile;


}
