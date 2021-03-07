package com.controller.dto.usercenter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增留言内容
 * @author roger
 * @since  2021/1/23 12:35
 */
@Data
@ApiModel("新增留言内容")
public class InsLeaveWordsDto {
    /**
     * 留言内容
     */
    @ApiModelProperty("留言内容")
    @NotBlank(message = "留言内容不能为空")
    private String content;
    @ApiModelProperty("被留言的人")
    @NotNull(message = "被留言的人的id不能为空")
    private Long userId;
}
