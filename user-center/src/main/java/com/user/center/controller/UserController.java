package com.user.center.controller;

import com.common.pojo.Result;
import com.common.utils.constant.ConstantUtils;
import com.controller.controller.user.UserCenterUserController;
import com.controller.dto.RegisterDTO;
import com.controller.dto.usercenter.UpdUserInfoDto;
import com.user.center.service.UserServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/**
 * 用户中心——接口层
 * @author roger
 * @since  · 10:30
 */
@RestController
public class UserController implements UserCenterUserController {


    private UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }



    @Override
    public Result selUserInfo(@RequestHeader(ConstantUtils.FRONT_KEY_AUTHORIZATION)String authentication) {
        return userService.selUserInfo(authentication);
    }

    @Override
    public Result updUserInfo(String authentication, UpdUserInfoDto updUserInfoDto) {
        return userService.updUserInfo(authentication,updUserInfoDto);
    }
}
