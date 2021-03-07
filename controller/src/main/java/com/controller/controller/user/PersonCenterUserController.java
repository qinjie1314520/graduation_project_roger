package com.controller.controller.user;

import com.common.pojo.Result;
import com.common.utils.constant.ConstantUtils;
import com.controller.dto.usercenter.InsLeaveWordsDto;
import com.controller.dto.usercenter.InsSelfIntroductionDto;
import com.controller.dto.usercenter.UpdSelfIntroductionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务——个人博客中心相关接口
 * @author roger
 * @since  2020/11/5 16:28
 */
@Api(tags = "用户服务——个人博客中心相关接口")
public interface PersonCenterUserController {
    /**
      * 个人博客——获得轮播图
      * @param userId 用户id
      * @return com.common.pojo.Result
      */
    @ApiOperation(value = "个人博客——获得轮播图")
    @RequestMapping(value = "/person-center/selCarousel",method = RequestMethod.GET)
    Result selCarousel(Long userId);
    /**
      * 个人博客——获得个人信息
      * @param userId 用户id
      * @return com.common.pojo.Result
      */
    @ApiOperation(value = "个人博客——获得个人信息")
    @RequestMapping(value = "/person-center/selSelfIntroduction",method = RequestMethod.GET)
    Result selSelfIntroduction(Long userId);
    /**
      * 个人博客——留言列表
      * @param id 目标用户id
      * @param pageNum 页码
      * @param pageSize 页大小
      * @return com.common.pojo.Result
      */
    @ApiOperation(value = "个人博客——留言列表")
    @RequestMapping(value = "/person-center/selLeaveList",method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页码",name = "pageNum",required = true,example = "1"),
            @ApiImplicitParam(value = "页大小",name = "pageSize",required = true,example = "1")
    })
    Result selLeaveList(@RequestParam(value = "id",defaultValue = "1")Long id,
                        @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                        @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize);
    /**
     * 个人博客——新增留言
     * @param content 相关参数
     * @param authentication 头部的鉴权信息
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "个人博客——新增留言")
    @RequestMapping(value = "/person-center/insLeaveWords",method = RequestMethod.POST)
    Result insLeaveWords( @RequestBody InsLeaveWordsDto content, @RequestHeader(ConstantUtils.FRONT_KEY_AUTHORIZATION)String authentication);
    /**
     * 个人博客——新增自我介绍
     * @param content 相关参数
     * @param authentication 头部的鉴权信息
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "个人博客——新增自我介绍")
    @RequestMapping(value = "/person-center/insSelfIntroduction",method = RequestMethod.POST)
    Result insSelfIntroduction (@RequestBody InsSelfIntroductionDto content,@RequestHeader(ConstantUtils.FRONT_KEY_AUTHORIZATION)  String authentication);
    /**
     * 个人博客——更新自我介绍
     * @param updSelfIntroductionTitleDto 相关参数
     * @param authentication 头部的鉴权信息
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "个人博客——更新自我介绍标题")
    @RequestMapping(value = "/person-center/updSelfIntroduction",method = RequestMethod.POST)
    Result updSelfIntroduction (@RequestBody UpdSelfIntroductionDto updSelfIntroductionTitleDto, @RequestHeader(ConstantUtils.FRONT_KEY_AUTHORIZATION)  String authentication);

    
}
