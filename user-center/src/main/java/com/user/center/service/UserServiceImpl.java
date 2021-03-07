package com.user.center.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.pojo.RRException;
import com.common.pojo.Result;
import com.common.utils.CommonUtils;
import com.common.utils.JwtUtils;
import com.common.utils.constant.ConstantUtils;
import com.controller.controller.user.UserCenterUserController;
import com.controller.dto.RegisterDTO;
import com.controller.dto.usercenter.UpdUserInfoDto;
import com.model.entity.GraduationUser;
import com.model.pojo.User;
import com.user.center.mapper.UserMapper;
import com.user.center.utils.PBKDF2Util;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
public class UserServiceImpl implements UserCenterUserController {
    private UserMapper userMapper;
    @Value("${jwt.secret}")
    private String jwtSecret;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }



    @Override
    public Result selUserInfo(String authentication) {
        Claims claimsFromJwt = JwtUtils.getClaimsFromJwt(jwtSecret, authentication);
        GraduationUser graduationUser = userMapper.selectById("" + claimsFromJwt.get(ConstantUtils.JWT_CLAIMS_FIELD_ID));
        User user = new User();
        user.setId(graduationUser.getId());
        user.setName(graduationUser.getUsername());
        user.setProfile(graduationUser.getProfile());
        user.setAccount(graduationUser.getAccount());
        user.setEmail(graduationUser.getEmail());
        user.setPhone(graduationUser.getPhone());
        if (graduationUser.getGender() == GraduationUser.GENDER_NUM_0) {
            user.setGender( GraduationUser.GENDER_STR_0);
        }else{
            user.setGender( GraduationUser.GENDER_STR_1);
        }
        return Result.ok(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updUserInfo(String authentication, UpdUserInfoDto updUserInfoDto) {
        Claims claimsFromJwt = JwtUtils.getClaimsFromJwt(jwtSecret, authentication);
        GraduationUser graduationUser = new GraduationUser();
        graduationUser.setEmail(updUserInfoDto.getEmail());
        graduationUser.setUsername(updUserInfoDto.getName());
        graduationUser.setPhone(updUserInfoDto.getPhone());

        if (GraduationUser.GENDER_STR_0.equals(updUserInfoDto.getGender())) {
            graduationUser.setGender( GraduationUser.GENDER_NUM_0);
        }else{
            graduationUser.setGender( GraduationUser.GENDER_NUM_1);
        }
        graduationUser.setProfile(updUserInfoDto.getProfile());
        graduationUser.setId(claimsFromJwt.get(ConstantUtils.JWT_CLAIMS_FIELD_ID,Long.class));
        userMapper.updateById(graduationUser);
        return Result.ok();
    }

}
