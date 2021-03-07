package com.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "注册时DTO")
public class RegisterDTO {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",required = true,example = "asdsad")
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",required = true,example = "asdsad")
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱",required = true,example = "1@qq.com")
    @Email(message = "邮箱不正确")
    private String email;
    /**
     * 邮箱验证码
     */
    @ApiModelProperty(value = "邮箱验证码",required = true,example = "121231")
    @NotBlank(message = "验证码不能为空")
    private String code;
}
