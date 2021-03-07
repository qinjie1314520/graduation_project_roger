package com.article.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 热门文章
 * @author roger
 * @since  2020/12/7 14:15
 */
@Data
@ApiModel("热门文章")
public class HotArticle {
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
     * 图片地址
     */
    @ApiModelProperty("图片地址")
    private String imageUrl;
    /**
     * 时间，毫秒时间戳
     */
    @ApiModelProperty("时间，毫秒时间戳")
    private String time;
    /**
     * 观看次数
     */
    @ApiModelProperty("观看次数")
    private Integer viewingTimes;
}
