package com.controller.controller.article;

import com.common.pojo.Result;
import com.common.utils.constant.ConstantUtils;
import com.controller.dto.usercenter.UpdBlogCollectDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户阅读历史——相关接口
 * @author roger
 * @since  2020/11/3 13:17
 */
@Api(tags = "用户阅读历史——相关接口")
public interface ArticleHistoryControllerInterface {

    /**
      * 获取用户阅读历史列表
      * @param pageNum 页码
      * @param pageSize 页大小
      * @return
      */
    @ApiOperation(value = "获取用户阅读历史列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页码", name = "pageNum", required = true, example = "1"),
            @ApiImplicitParam(value = "页大小", name = "pageSize", required = true, example = "1")
    })
    @RequestMapping(value = "/article/history/selHistoryBlogList",method = RequestMethod.GET)
    Result selHistoryBlogList(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                          @RequestHeader(ConstantUtils.FRONT_KEY_AUTHORIZATION) String authorization);

}
