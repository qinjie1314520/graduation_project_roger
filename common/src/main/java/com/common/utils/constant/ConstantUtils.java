package com.common.utils.constant;

import io.jsonwebtoken.SignatureAlgorithm;
/**
 * 通用相关常量
 * @author roger
 * @since  2020/11/9 16:22
 */
public class ConstantUtils {

    /**
     * JWT加密算法
     */
    public final static SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    /**
     * 邮箱验证码登录场景
     */
    public final static int EMAIL_VERIFICATION_CODE_SCENE_AT_LOGIN = 1;
    /**
     * 邮箱验证码注册场景
     */
    public final static int EMAIL_VERIFICATION_CODE_SCENE_AT_REGISTER = 2;

    /**
     * 手机验证码登录场景
     */
    public final static int PHONE_VERIFICATION_CODE_SCENE_AT_LOGIN = 1;
    /**
     * 手机验证码注册场景
     */
    public final static int PHONE_VERIFICATION_CODE_SCENE_AT_REGISTER = 2;

    /**
     * 鉴权中心的服务名
     */
    public final static String SERVICE_NAME_OF_AUTHORITY = "graduation-project-roger-authority";
    /**
     * 通知中心的服务名
     */
    public final static String SERVICE_NAME_OF_NOTIFICATION = "graduation-project-roger-notification";
    /**
     * 网关的服务名
     */
    public final static String SERVICE_NAME_OF_GATEWAY = "graduation-project-roger-gateway";
    /**
     * 用户中心的服务名
     */
    public final static String SERVICE_NAME_OF_USERCENTER = "graduation-project-roger-user-center";

    /**
     * jwt中保存的字段名——用户名
     */
    public final static String JWT_CLAIMS_FIELD_USERNAME = "uUsername";
    /**
     * jwt中保存的字段名——用户id
     */
    public final static String JWT_CLAIMS_FIELD_ID = "uId";
    /**
     * jwt中保存的字段名——角色ids
     */
    public final static String JWT_CLAIMS_FIELD_ROLE_IDS = "uRoleIds";
    /**
     * jwt中保存的字段名——到期时间
     */
    public final static String JWT_CLAIMS_FIELD_EXPIRE = "expire";

    /**
     * 前端放到head的授权信息的key的名字
     */
    public final static String FRONT_KEY_AUTHORIZATION = "Authorization";



    /**
     * 项目服务名（前缀名）——用户中心
     */
    public final static String GRADUATION_PROJECT_ROGER_USER_CENTER = "graduation-project-roger-user-center";
    /**
     * 项目服务名（前缀名）——文章
     */
    public final static String GRADUATION_PROJECT_ROGER_ARTICLE = "graduation-project-roger-article";
    /**
     * 项目服务名（前缀名）——鉴权
     */
    public final static String GRADUATION_PROJECT_ROGER_AUTHORITY = "graduation-project-roger-authority";
    /**
     * 项目服务名（前缀名）——通知
     */
    public final static String GRADUATION_PROJECT_ROGER_NOTIFICATION = "graduation-project-roger-notification";
}
