package com.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
/**
 * 查询最新文章参数
 * @author roger
 * @since  2020/12/7 14:28
 */
@Data
@ApiModel("查询最新文章参数")
public class SelectLatestArticleDTO {
    /**
     * 文章类型，根据文章类型中的value
     */
    @ApiModelProperty("文章类型，根据文章类型中的value")
    @NotNull(message = "文章类型不能为空")
    private Integer type;
    /**
     * 页码
     */
    @ApiModelProperty("页码")
    @Min(message = "页码最小为1", value = 1)
    private Integer pageNum = 1;
    /**
     * 页大小
     */
    @ApiModelProperty("页大小")
    @Min(message = "页大小最小为1", value = 1)
    private Integer pageSize=10;
}
