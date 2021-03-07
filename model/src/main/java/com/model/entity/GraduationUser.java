package com.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;
import static com.baomidou.mybatisplus.annotation.IdType.AUTO;

@ToString
@TableName(value = "graduation_user")
@Data
public class GraduationUser {
  @TableId(type = AUTO)
  private Long id;
  private String username;
  private String password;
  private String salt;
  private String phone;
  private String email;
  private String profile;
  private String account;
  private Integer gender;
  /**
   * 性别的状态数字——0
   */
  public static final int GENDER_NUM_0 = 0;
  /**
   * 性别的字符数字——男
   */
  public static final String GENDER_STR_0 = "男";
  /**
   * 性别的状态数字——1
   */
  public static final int GENDER_NUM_1 = 1;
  /**
   * 性别的字符数字——女
   */
  public static final String GENDER_STR_1 = "女";
}
