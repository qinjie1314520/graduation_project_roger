package com.controller.controller.article;

import com.common.pojo.Result;
import com.common.utils.constant.ConstantUtils;
import com.controller.dto.SelectLatestArticleDTO;
import com.controller.dto.usercenter.UpdBlogCollectDto;
import com.controller.pojo.NameAndValue;
import com.controller.pojo.NameAndValueAndChildren;
import com.controller.pojo.PageInfoResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户收藏——相关接口
 * @author roger
 * @since  2020/11/3 13:17
 */
@Api(tags = "用户收藏——相关接口")
public interface ArticleCollectControllerInterface {
    /**
      * 更新收藏状态
      * @param id 博客id
      * @return com.common.pojo.Result
      */
    @ApiOperation(value = "更新收藏状态")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "博客id", name = "id", required = true, example = "1")
    })
    @RequestMapping(value = "/article/collect/updBlogCollect",method = RequestMethod.POST)
    Result updBlogCollect(@RequestBody @Validated UpdBlogCollectDto updBlogCollectDto, @RequestHeader(ConstantUtils.FRONT_KEY_AUTHORIZATION) String authorization);

    /**
      * 获取用户收藏列表
      * @param pageNum 页码
      * @param pageSize 页大小
      * @return
      */
    @ApiOperation(value = "获取用户收藏列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页码", name = "pageNum", required = true, example = "1"),
            @ApiImplicitParam(value = "页大小", name = "pageSize", required = true, example = "1"),
            @ApiImplicitParam(value = "标题搜索内容", name = "content", required = false, example = "1")
    })
    @RequestMapping(value = "/article/collect/selCollectList",method = RequestMethod.GET)
    Result selCollectList(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                         String content,
                          @RequestHeader(ConstantUtils.FRONT_KEY_AUTHORIZATION) String authorization);

}
