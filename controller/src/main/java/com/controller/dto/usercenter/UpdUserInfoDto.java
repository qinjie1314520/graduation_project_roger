package com.controller.dto.usercenter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
/**
 * 更新用户信息
 * @author roger
 * @since  2021/3/1 9:24
 */
@ApiModel("更新用户信息")
@Data
public class UpdUserInfoDto {
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("头像")
    private String profile;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("性别")
    private String gender;
}
