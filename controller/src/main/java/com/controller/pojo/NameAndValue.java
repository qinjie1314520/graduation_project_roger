package com.controller.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 名称and value
 * @author roger
 * @since  2020/12/7 14:09
 */
@Data
@ApiModel("名称+value")
@AllArgsConstructor
@NoArgsConstructor
public class NameAndValue {
    /**
     * 名称
     */
    @ApiModelProperty("名称")
    protected String name;
    /**
     * 值
     */
    @ApiModelProperty("值")
    protected Object value;
}
