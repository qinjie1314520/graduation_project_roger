package com.authority.controller;

import com.authority.service.AuthServiceImpl;
import com.common.pojo.BinarySearchAuthorityTree;
import com.common.pojo.ParameterVerificationException;
import com.common.pojo.Result;
import com.common.utils.CommonUtils;
import com.controller.controller.AuthorityAuthControllerInterface;
import com.controller.dto.LoginDTO;
import com.controller.dto.RegisterDTO;
import com.controller.validator.DTOValidatorAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
public class AuthController implements AuthorityAuthControllerInterface {
    private AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }


    @Override
    public Result selPrivateKey(){

        return authService.selPrivateKey();
    }
    @Override
    @DTOValidatorAnnotation
    public Result login(@RequestBody LoginDTO loginDTO){
        return authService.login(loginDTO);
    }

    @Override
    public Result selLoginCode(String phoneOrEmail) {
        if (CommonUtils.isPhone(phoneOrEmail) || CommonUtils.isEmail(phoneOrEmail)) {
            //标识手机或者邮箱
            return authService.selLoginCode(phoneOrEmail);
        }
        throw new ParameterVerificationException("参数错误");
    }

    @Override
    public Result<BinarySearchAuthorityTree> selRolesAuthorities(Long roleId) {
        if(roleId==null || roleId < 0){
            throw new ParameterVerificationException("参数错误");
        }
        return authService.selRolesAuthorities(roleId);
    }

    @Override
    public Result register(RegisterDTO registerDTO) {
        return authService.register(registerDTO);
    }

    @Override
    public Result selRegisterCode(String email) {
        return authService.selRegisterCode(email);
    }
}
