package com.common.utils.constant;

/**
 * 鉴权中心的相关常量
 * @author roger
 * @since  2020/11/9 16:22
 */
public class AuthorityConstantUtils {

    /**
     * 登录时邮箱验证码前端给的状态
     */
    public final static int LOGIN_TYPE_EMAIL = 3;
    /**
     * 登录手机号验证码前端给的状态
     */
    public final static int LOGIN_TYPE_PHONE = 2;
    /**
     * 登录用户密码前端给的状态
     */
    public final static int LOGIN_TYPE_USERNAME = 1;

    /**
     * 接口——获取密匙
     */
    public final static String SERVICE_NAME_AUTHORITY_OF_URL_AUTHORITY_SELPRIVATEKEY = "/authority/selPrivateKey";
    /**
     * 接口——获取角色权限树
     */
    public final static String SERVICE_NAME_AUTHORITY_OF_URL_AUTHORITY_SELROLEAUTHORITIES = "/authority/selRoleAuthorities";


}
