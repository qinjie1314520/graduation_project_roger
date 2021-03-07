package com.article.pojo;

import com.controller.pojo.NameAndValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 最新文章
 * @author roger
 * @since  2020/12/7 15:16
 */
@Data
@ApiModel("最新文章")
public class LatestArticle {
    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;
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
    /**
     * 文章描述
     */
    @ApiModelProperty("文章描述")
    private String description;
    /**
     * 文章类型
     */
    @ApiModelProperty("文章类型")
    private String type;

    /**
     * 文章类型
     */
    @ApiModelProperty("是否被收藏，0-没收藏，1-收藏")
    private Integer isCollect;
    /**
     * 0-没收藏
     */
    public static final int COLLECT = 1;
    /**
     * 1-收藏
     */
    public static final int NOT_COLLECT = 0;
}
