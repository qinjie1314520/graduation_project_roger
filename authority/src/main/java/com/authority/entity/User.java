package com.authority.entity;

import lombok.Data;

import java.util.List;
@Data
public class User {
    private Integer uId;
    private String uUsername;
    private String uPassword;
    private String uSalt;
    private String uPhone;
    private String uEmail;
    private List<Long> uRoleIds;
}
