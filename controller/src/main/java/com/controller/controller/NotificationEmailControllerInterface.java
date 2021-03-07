package com.controller.controller;

import com.common.pojo.Result;
import com.controller.dto.CodeValidationDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 邮箱通知相关接口
 * @author roger
 * @since  2020/11/3 13:17
 */
@Api(tags = "通知服务——邮箱相关接口")
public interface NotificationEmailControllerInterface {

    /**
     * 校验邮箱验证码是否正确
     * @param codeValidationDTO 邮箱+验证码
     * @return graduation.project.roger.pojo.Result
     */
    @ApiOperation(value = "校验邮箱验证码是否正确")
    @RequestMapping(value = "/notification/validateCode",method = RequestMethod.GET)
    Result codeEmailValidate(CodeValidationDTO codeValidationDTO);



    /**
     * 注册时获得邮箱验证码
     * @param email 邮箱
     * @param emailSceneId 模板id，1-登录，2-注册
     * @return graduation.project.roger.pojo.Result
     */
    @ApiOperation(value = "发送邮箱验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email",value = "邮箱",required = true,example = "1@qq.com"),
            @ApiImplicitParam(name = "emailSceneId",value = "模板id，1-登录，2-注册",required = true,example = "1")
    })
    @RequestMapping(value = "/notification/sendEmailCode",method = RequestMethod.GET)
    Result sendEmailCode(String email, Integer emailSceneId);

}
