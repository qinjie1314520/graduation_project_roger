package com.article.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 每日一句
 * @author roger
 * @since  2020/12/8 14:52
 */
@Data
@ApiModel("每日一句")
public class DailySentence {
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTime;
    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;
}
