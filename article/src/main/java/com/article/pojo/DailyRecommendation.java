package com.article.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 每日推荐
 * @author roger
 * @since  2020/12/8 15:12
 */
@Data
@ApiModel("每日推荐")
public class DailyRecommendation {
    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;
    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;
}
