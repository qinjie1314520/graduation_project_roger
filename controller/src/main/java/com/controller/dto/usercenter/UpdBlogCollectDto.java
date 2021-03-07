package com.controller.dto.usercenter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
/**
 * 更新收藏相关参数
 * @author roger
 * @since  2021/3/1 13:44
 */
@Data
public class UpdBlogCollectDto {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;
}
