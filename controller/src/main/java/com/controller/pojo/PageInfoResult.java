package com.controller.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
/**
 * 分页数据信息
 * @author roger
 * @since  2020/12/7 14:25
 */
@Data
@ApiModel(value = "分页数据信息")
public class PageInfoResult<T> {

    List<T> list;
    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    Integer pageNum;
    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    Integer pageSize;
    /**
     * 总数
     */
    @ApiModelProperty(value = "总数")
    Long total;
}
