package com.controller.dto.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
/**
 * 个人博客——博客管理列表参数
 * @author roger
 * @since  2021/1/24 18:51
 */
@Data
@ApiModel("个人博客——博客管理列表参数")
public class SelPersonBlogListDto {
    @ApiModelProperty("页码")
    @NotNull(message = "页码参数不能为空")
    private Integer pageNum;
    @ApiModelProperty("页大小")
    @NotNull(message = "页大小参数不能为空")
    private Integer pageSize;
    @ApiModelProperty("搜索内容")
    private String content;

    @ApiModelProperty("id")
    @NotNull(message = "id参数不能为空")
    private Long id;
}
