package com.authority.controller.login;

import com.common.pojo.Result;

/**
  * 登陆时策略模式实现
  * @Author qinjie
  * @CreateDate 2020-11-02 22:36
 */
public interface LoginHandler {
    /**
     * 登陆类型，登录类型，1-用户密码，2-手机号验证码，3-邮箱验证码
     * @return java.lang.Integer
     */
    Integer getLoginType();

    /**
     * 处理登陆
     * @param username 用户名
     * @param password 密码
     * @return com.common.pojo.Result
     */
    Result handleLogin(String username,String password);
}
