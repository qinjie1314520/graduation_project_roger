package com.model.pojo;

import lombok.Data;

import java.util.List;

/**
 * jwt的用户
 * @author roger
 * @since  2021/1/23 10:36
 */
@Data
public class JwtUser {
    /**
     * id
     */
    private Integer uId;
    /**
     * 用户名
     */
    private String uUsername;
    /**
     * 角色ids
     */
    private List<Long> uRoleIds;
}
