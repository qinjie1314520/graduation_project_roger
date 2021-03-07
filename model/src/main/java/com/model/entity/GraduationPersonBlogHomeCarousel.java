package com.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 个人博客——首页轮播
 * @author roger
 * @since  2021/1/23 10:50
 */
public class GraduationPersonBlogHomeCarousel {
  private Long id;
  private String imageUrl;
  private Long homeId;
  private Long userId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }


  public Long getHomeId() {
    return homeId;
  }

  public void setHomeId(Long homeId) {
    this.homeId = homeId;
  }

}
