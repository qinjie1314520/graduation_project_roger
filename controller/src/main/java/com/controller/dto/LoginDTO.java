package com.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 登录时参数
 * @author roger
 * @since  2020/11/3 13:44
 */
@Data
@ApiModel(value = "登录时参数")
public class LoginDTO {
    /**
     * 用户名或者是手机号或者是邮箱
     */
    @ApiModelProperty(value = "用户名或者是手机号或者是邮箱",required = true,example = "12121")
    @NotBlank(message = "账户不能为空")
    private String username;
    /**
     * 密码或者是验证码
     */
    @ApiModelProperty(value = "密码或者是验证码",required = true,example = "adasdasdqww123")
    @NotBlank(message = "密码或验证码不能为空")
    private String password;
    /**
     * 登录类型，1-用户密码，2-手机号验证码，3-邮箱验证码
     */
    @ApiModelProperty(value = "登录类型，1-用户密码，2-手机号验证码，3-邮箱验证码",required = true,example = "1")
    @NotNull(message = "登录类型不能为空")
    @Min(value = 1,message = "最小为1")
    @Max(value = 3,message = "最大为3")
    private Integer loginType;

}
