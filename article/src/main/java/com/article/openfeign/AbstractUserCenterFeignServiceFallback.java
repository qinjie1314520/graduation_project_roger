package com.article.openfeign;


import com.common.pojo.ExceptionEnum;
import com.common.pojo.Result;
import com.controller.dto.CodeValidationDTO;
import com.model.pojo.User;

/**
 * @author PC
 */
public abstract class AbstractUserCenterFeignServiceFallback implements UserCenterFeignService {
    @Override
    public Result<User> selUserInfo(String authentication) {
        return Result.error(ExceptionEnum._503);
    }
}
