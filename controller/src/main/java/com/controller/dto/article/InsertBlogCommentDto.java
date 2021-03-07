package com.controller.dto.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 博客详情——评论参数
 * @author roger
 * @since  2021/2/22 17:52
 */
@Data
@ApiModel("博客详情——评论")
public class InsertBlogCommentDto {
    @ApiModelProperty("内容")
    @NotBlank(message = "内容不能为空")
    private String content;
    @ApiModelProperty("地址")
    @NotBlank(message = "地址不能为空")
    private String location;
    @ApiModelProperty("文章id")
    @NotNull(message = "文章id不能为空")
    private Long articleId;
}
