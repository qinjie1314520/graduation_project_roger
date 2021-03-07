package com.controller.dto.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 个人博客——文章更新
 * @author roger
 * @since  2021/1/24 19:03
 */
@Data
@ApiModel("个人博客——文章更新")
public class UpdPersonBlogDto {
    @ApiModelProperty("文章id")
    @NotNull(message = "文章id不能为空")
    private Long id;
    @ApiModelProperty("文章内容")
    @NotBlank(message = "文章内容不能为空")
    private String content;
    @ApiModelProperty("文章标题")
    @NotBlank(message = "文章标题不能为空")
    private String title;
    @ApiModelProperty("封面图")
    @NotBlank(message = "封面图不能为空")
    private String coverImage;
    @NotBlank(message = "文章描述不能为空")
    @ApiModelProperty("文章描述")
    private String description;
    @NotEmpty(message = "文章类型id不能为空")
    @ApiModelProperty("文章类型id")
    private List<Long> type;
    @NotNull(message = "文章栏目不能为空")
    @ApiModelProperty("文章栏目id")
    private Long programa;
}
