package com.controller.dto.usercenter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 个人博客——新增自我介绍
 * @author roger
 * @since  2021/1/24 13:05
 */
@ApiModel("个人博客——新增自我介绍")
@Data
public class InsSelfIntroductionDto {
    @ApiModelProperty("html的内容")
    @NotBlank(message = "内容不能为空")
    private String content;
}
