package com.model.entity;

/**
 * 个人博客——博客首页
 * @author roger
 * @since  2021/1/23 10:50
 */
public class GraduationPersonBlogHome {

  private long id;
  private String footer;
  private String additionalBody;
  private long userId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getFooter() {
    return footer;
  }

  public void setFooter(String footer) {
    this.footer = footer;
  }


  public String getAdditionalBody() {
    return additionalBody;
  }

  public void setAdditionalBody(String additionalBody) {
    this.additionalBody = additionalBody;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

}
