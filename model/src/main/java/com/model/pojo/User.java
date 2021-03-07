package com.model.pojo;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String account;
    private String profile;
    private String email;
    private String phone;
    private String gender;
}
