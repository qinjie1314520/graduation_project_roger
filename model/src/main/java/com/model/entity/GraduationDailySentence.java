package com.model.entity;


public class GraduationDailySentence {

  private long id;
  private String content;
  private long createTime;
  private String showDay;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }


  public String getShowDay() {
    return showDay;
  }

  public void setShowDay(String showDay) {
    this.showDay = showDay;
  }

}
