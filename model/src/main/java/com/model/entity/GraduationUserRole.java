package com.model.entity;

import lombok.Data;

@Data
public class GraduationUserRole {

  private Long userId;
  private Long roleId;
  /**
   * 普通用户角色的id
   */
  public static final long ROLE_ID_Normal_User = 1;

}
