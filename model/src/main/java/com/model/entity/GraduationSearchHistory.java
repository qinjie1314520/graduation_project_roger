package com.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class GraduationSearchHistory {
  @TableId
  private Long id;
  private Long userId;
  private Long blogId;
  private Long createTime;



}
