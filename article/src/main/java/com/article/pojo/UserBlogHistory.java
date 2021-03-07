package com.article.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 用户历史博客列表
 * @author roger
 * @since  2021/3/1 14:24
 */
@Data
@ApiModel("用户历史博客列表")
public class UserBlogHistory {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("图片地址")
    private String imageUrl;
    @ApiModelProperty("描述")
    private String description;

}
