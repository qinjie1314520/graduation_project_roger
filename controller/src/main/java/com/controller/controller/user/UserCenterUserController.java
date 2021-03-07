package com.controller.controller.user;

import com.common.pojo.Result;
import com.common.utils.constant.ConstantUtils;
import com.controller.dto.RegisterDTO;
import com.controller.dto.usercenter.UpdUserInfoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户服务——用户相关接口
 * @author roger
 * @since  2020/11/5 16:28
 */
@Api(tags = "用户服务——用户相关接口")
public interface UserCenterUserController {


    /**
     * 查询用户信息
     * @param authentication header中的鉴权信息
     * @return graduation.project.roger.pojo.Result
     */
    @ApiOperation(value = "查询用户信息")
    @RequestMapping(value = "/user-center/selUserInfo",method = RequestMethod.GET)
    Result selUserInfo(String authentication);
    /**
     * 更新用户信息
     * @param authentication header中的鉴权信息
     * @return graduation.project.roger.pojo.Result
     */
    @ApiOperation(value = "更新用户信息")
    @RequestMapping(value = "/user-center/updUserInfo",method = RequestMethod.POST)
    Result updUserInfo(@RequestHeader(ConstantUtils.FRONT_KEY_AUTHORIZATION)String authentication, @RequestBody @Validated UpdUserInfoDto updUserInfoDto);
}
