package com.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;
import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

@TableName(value = "graduation_home_rotation")
public class GraduationHomeRotation {
  public final static int STATUS_VALUE_SHOW = 1;
  public final static int STATUS_VALUE_NOT_SHOW = 0;
  @TableId(type = AUTO)
  private Long id;
  private String imageUrl;
  private String imageJumpAddress;
  private Integer status;


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


  public String getImageJumpAddress() {
    return imageJumpAddress;
  }

  public void setImageJumpAddress(String imageJumpAddress) {
    this.imageJumpAddress = imageJumpAddress;
  }


  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

}
