package com.controller.controller.article;

import com.common.pojo.Result;
import com.common.utils.constant.ConstantUtils;
import com.controller.dto.SelectLatestArticleDTO;
import com.controller.dto.article.*;
import com.controller.dto.usercenter.InsSelfIntroductionDto;
import com.controller.pojo.NameAndValue;
import com.controller.pojo.PageInfoResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 个人博客——博客管理
 * @author roger
 * @since  2020/11/3 13:17
 */
@Api(tags = "个人博客——博客管理")
public interface PersonBlogControllerInterface {
    /**
     * 个人博客——博客列表
     * @param selPersonBlogListDto 相关参数
     * @param authentication 头部的鉴权信息
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "个人博客——博客列表")
    @RequestMapping(value = "/article/blog/selPersonBlogList",method = RequestMethod.GET)
    Result selPersonBlogList(SelPersonBlogListDto selPersonBlogListDto, @RequestHeader(ConstantUtils.FRONT_KEY_AUTHORIZATION)  String authentication);
    /**
     * 个人博客——博客删除
     * @param delPersonBlogDto 相关参数
     * @param authentication 头部的鉴权信息
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "个人博客——博客列表")
    @RequestMapping(value = "/article/blog/delPersonBlog",method = RequestMethod.DELETE)
    Result delPersonBlog(@RequestBody DelPersonBlogDto delPersonBlogDto, @RequestHeader(ConstantUtils.FRONT_KEY_AUTHORIZATION)  String authentication);
    /**
     * 个人博客——博客新增
     * @param insPersonBlogDto 相关参数
     * @param authentication 头部的鉴权信息
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "个人博客——博客新增")
    @RequestMapping(value = "/article/blog/insPersonBlog",method = RequestMethod.PUT)
    Result insPersonBlog(@RequestBody @Validated InsPersonBlogDto insPersonBlogDto, @RequestHeader(ConstantUtils.FRONT_KEY_AUTHORIZATION)  String authentication);
    /**
     * 个人博客——博客更新
     * @param updPersonBlogDto 相关参数
     * @param authentication 头部的鉴权信息
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "个人博客——博客更新")
    @RequestMapping(value = "/article/blog/updPersonBlog",method = RequestMethod.POST)
    Result updPersonBlog(@RequestBody @Validated UpdPersonBlogDto updPersonBlogDto, @RequestHeader(ConstantUtils.FRONT_KEY_AUTHORIZATION)  String authentication);

    /**
     * 个人博客——博客详情
     *
     * @param id 博客id
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "个人博客——博客详情")
    @RequestMapping(value = "/article/blog/details", method = RequestMethod.GET)
    Result personBlogDetails(Long id,@RequestHeader(name=ConstantUtils.FRONT_KEY_AUTHORIZATION,required = false) String authentication);

    /**
     * 博客详情——获得相关推荐
     *
     * @param authentication jwt
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "博客详情——获得相关推荐")
    @RequestMapping(value = "/article/blog/detailsRecommend", method = RequestMethod.GET)
    Result personBlogDetailsRecommend(@RequestHeader(name=ConstantUtils.FRONT_KEY_AUTHORIZATION,required = false) String authentication);

    /**
     * 博客详情——评论列表
     *
     * @param id id
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "博客详情——评论列表")
    @RequestMapping(value = "/article/blog/detailsComments", method = RequestMethod.GET)
    Result personBlogDetailsComments(Long id,Integer pageNum,Integer pageSize);
    /**
     * 博客详情——评论
     *
     * @param insertBlogCommentDto 评论内容
     * @param authentication 鉴权信息
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "博客详情——评论")
    @RequestMapping(value = "/article/blog/comment", method = RequestMethod.PUT)
    Result insertBlogComment(@RequestBody InsertBlogCommentDto insertBlogCommentDto,@RequestHeader(ConstantUtils.FRONT_KEY_AUTHORIZATION) String authentication);
}
