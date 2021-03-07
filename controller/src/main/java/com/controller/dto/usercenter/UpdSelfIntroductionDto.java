package com.controller.dto.usercenter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
/**
 * 自我介绍——更新
 * @author roger
 * @since  2021/2/19 17:34
 */
@ApiModel("自我介绍——更新")
@Data
public class UpdSelfIntroductionDto {
    @ApiModelProperty("中文标题")
    private String titleCh;
    @ApiModelProperty("英文标题")
    private String titleEn;
    @ApiModelProperty("图片地址")
    private String imageUrl;
    @ApiModelProperty("详情html")
    private String content;

}
