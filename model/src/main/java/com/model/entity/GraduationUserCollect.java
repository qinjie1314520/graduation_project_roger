package com.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class GraduationUserCollect {
  private Long blogId;
  private Long userId;
  private Long createTime;

}
