package com.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 校验邮箱或者手机验证码是否正确
 * @author roger
 * @since  2020/11/4 10:46
 */
@Data
@ApiModel(value = "校验邮箱或者手机验证码时需要参数")
public class CodeValidationDTO {
    /**
     * 邮箱或手机
     */
    @ApiModelProperty(value = "邮箱或手机",required = true,example = "1@qq.com")
    @NotBlank(message = "邮箱或手机号不能为空")
    private String emailOrPhone;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码",required = true,example = "123123")
    @NotBlank(message = "验证码不能为空")
    private String code;

    /**
     * 场景id，1-登录，2-注册
     */
    @ApiModelProperty(value = "场景id，1-登录，2-注册",required = true,example = "1")
    @NotNull(message = "场景id不能为空")
    private Integer sceneId;
}
