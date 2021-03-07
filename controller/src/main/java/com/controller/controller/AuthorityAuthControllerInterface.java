package com.controller.controller;

import com.common.pojo.Result;
import com.controller.dto.LoginDTO;
import com.controller.dto.RegisterDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 授权相关接口
 * @author roger
 * @since  2020/11/3 13:17
 */
@Api(tags = "鉴权服务——授权相关接口")
public interface AuthorityAuthControllerInterface {
    /**
      * 获取jwt的密匙
      * @return graduation.project.roger.pojo.Result
      */
    @ApiOperation(value = "获取jwt的密匙")
    @RequestMapping(value = "/authority/selPrivateKey",method = RequestMethod.GET)
    Result selPrivateKey();

    /**
      * 登录
      * @param loginDTO 保存登录时相关参数
      * @return graduation.project.roger.pojo.Result
      */
    @ApiOperation(value = "登陆")
    @RequestMapping(value = "/authority/login",method = RequestMethod.POST)
    Result login(LoginDTO loginDTO);

    /**
      * 登录时获取短信或者邮箱验证码
      * @param phoneOrEmail 手机号或者邮箱
      * @return graduation.project.roger.pojo.Result
      */

    @ApiOperation(value = "登录时获取短信或者邮箱验证码")
    @ApiImplicitParam(value = "邮箱或手机",name = "phoneOrEmail",required = true,example = "1@qq.com")
    @RequestMapping(value = "/authority/selLoginCode",method = RequestMethod.GET)
    Result selLoginCode(String phoneOrEmail);
    /**
     * 通过角色id是获得角色的权限
     * @param roleId 角色id
     * @return graduation.project.roger.pojo.Result
     */
    @ApiOperation(value = "通过角色id是获得角色的权限")
    @ApiImplicitParam(value = "角色id",name = "roleId",required = true,example = "1")
    @RequestMapping(value = "/authority/selRoleAuthorities",method = RequestMethod.GET)
    Result selRolesAuthorities(Long roleId);


    /**
     * 注册
     * @param registerDTO 相关注册时需要填写的信息
     * @return graduation.project.roger.pojo.Result
     */
    @ApiOperation(value = "注册")
    @RequestMapping(value = "/authority/register",method = RequestMethod.PUT)
    Result register(@RequestBody @Validated RegisterDTO registerDTO);
    /**
     * 注册发送邮箱验证码
     * @param email 邮箱
     * @return graduation.project.roger.pojo.Result
     */
    @ApiOperation(value = "注册发送邮箱验证码")
    @RequestMapping(value = "/authority/selRegisterCode",method = RequestMethod.GET)
    Result selRegisterCode(String email);
}
