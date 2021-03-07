package com.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;
import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

@Data
public class GraduationUserBlogHistory {
  @TableId(type = AUTO)
  private Long id;
  private Long blogId;
  private Long userId;
  private String username;
  private Long createTime;


}
