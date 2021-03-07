package com.controller.dto.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 个人博客——删除博客
 * @author roger
 * @since  2021/1/24 18:52
 */
@ApiModel("删除博客相关参数")
@Data
public class DelPersonBlogDto {
    @ApiModelProperty("id")
    @NotNull(message = "博客id不能为空")
    private Long id;
}
