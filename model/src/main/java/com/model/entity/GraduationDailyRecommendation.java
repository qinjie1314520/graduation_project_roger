package com.model.entity;

import lombok.Data;

@Data
public class GraduationDailyRecommendation {

  private long id;
  private String articleId;
  private String showDay;
  private long createTime;


}
