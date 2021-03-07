package com.controller.controller.admin;

import com.common.pojo.Result;
import com.controller.dto.LoginDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * 管理——提供的公用的接口服务
 *
 * @author roger
 * @since 2020/11/3 13:17
 */
@Api(tags = "管理端——提供的公用的接口服务")
public interface PublicControllerInterface {
    /**
      * 分页查询首页轮播图
      * @param pageNum 页码
      * @param pageSize 页大小
      * @return com.common.pojo.Result
      */
    @ApiOperation(value = "获得首页的轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页码", name = "pageNum", required = true, example = "1"),
            @ApiImplicitParam(value = "页大小", name = "pageSize", required = true, example = "1")
    })
    @RequestMapping(value = "/public/selHomeRotation", method = RequestMethod.GET)
    Result selHomeRotation(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(name = "pageSize", defaultValue = "1") Integer pageSize);

    /**
     * 公告
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return com.common.pojo.Result
     */
    @ApiOperation(value = "公告")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页码", name = "pageNum", required = true, example = "1"),
            @ApiImplicitParam(value = "页大小", name = "pageSize", required = true, example = "1")
    })
    @RequestMapping(value = "/public/selAnnouncement", method = RequestMethod.GET)
    Result selAnnouncement(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(name = "pageSize", defaultValue = "1") Integer pageSize);
}
