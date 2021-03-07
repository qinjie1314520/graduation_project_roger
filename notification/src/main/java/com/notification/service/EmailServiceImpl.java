package com.notification.service;

import com.common.pojo.ExceptionEnum;
import com.common.pojo.ParameterVerificationException;
import com.common.pojo.RRException;
import com.common.pojo.Result;
import com.common.utils.CommonUtils;
import com.common.utils.constant.ConstantUtils;
import com.common.utils.constant.NotificationConstantUtils;
import com.controller.controller.NotificationEmailControllerInterface;
import com.controller.dto.CodeValidationDTO;
import com.notification.redis.RedisService;
import com.notification.utils.SendEmailBy163;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements NotificationEmailControllerInterface {
    /**
     * 邮件显示的发件公司
     */
    @Value("${email.verification.code.own.name}")
    private String emailVerificationCodeOwnName;
    /**
     * 邮箱验证码长度
     */
    @Value("${email.verification.code.length}")
    private Integer emailVerificationCodeLength;
    /**
     * 邮箱验证码场景——登录验证码内容
     */
    @Value("${email.verification.code.scene.content.at.login}")
    private String emailVerificationCodeSceneContentAtLogin;
    /**
     * 邮箱验证码场景——注册验证码内容
     */
    @Value("${email.verification.code.scene.content.at.register}")
    private String emailVerificationCodeSceneContentAtRegister;


    /**
     * 邮箱验证码有效期
     */
    @Value("${email.verification.code.expire}")
    private Long emailVerificationCodeExpire;
    /**
     * 邮箱验证码场景——登录邮件主题
     */
    @Value("${email.verification.code.subject.at.login}")
    private String emailVerificationCodeSubjectAtLogin;
    /**
     * 邮箱验证码场景——注册邮件主题
     */
    @Value("${email.verification.code.subject.at.register}")
    private String emailVerificationCodeSubjectAtRegister;

    private RedisService redisService;

    public EmailServiceImpl(RedisService redisService) {
        this.redisService = redisService;
    }


    @Override
    public Result codeEmailValidate(CodeValidationDTO codeValidationDTO) {
        Object code = null;
        //变量redis保存时的前缀
        String str = null;
        if (CommonUtils.isEmail(codeValidationDTO.getEmailOrPhone())) {
            //邮件
            switch (codeValidationDTO.getSceneId()) {
                case ConstantUtils.EMAIL_VERIFICATION_CODE_SCENE_AT_LOGIN:
                    code = redisService.get(NotificationConstantUtils.REDIS_PREFIX_LOGIN_CODE_EMAIL + codeValidationDTO.getEmailOrPhone());
                    str = NotificationConstantUtils.REDIS_PREFIX_LOGIN_CODE_EMAIL;
                    break;
                case ConstantUtils.EMAIL_VERIFICATION_CODE_SCENE_AT_REGISTER:
                    code = redisService.get(NotificationConstantUtils.REDIS_PREFIX_REGISTER_CODE_EMAIL + codeValidationDTO.getEmailOrPhone());
                    str = NotificationConstantUtils.REDIS_PREFIX_REGISTER_CODE_EMAIL;
                    break;
                default:
                    throw new ParameterVerificationException("场景id错误！");
            }
        } else if (CommonUtils.isPhone(codeValidationDTO.getEmailOrPhone())) {
            //手机
            switch (codeValidationDTO.getSceneId()) {
                case ConstantUtils.PHONE_VERIFICATION_CODE_SCENE_AT_LOGIN:
                    code = redisService.get(NotificationConstantUtils.REDIS_PREFIX_LOGIN_CODE_PHONE + codeValidationDTO.getEmailOrPhone());
                    str = NotificationConstantUtils.REDIS_PREFIX_LOGIN_CODE_PHONE;
                    break;
                case ConstantUtils.PHONE_VERIFICATION_CODE_SCENE_AT_REGISTER:
                    code = redisService.get(NotificationConstantUtils.REDIS_PREFIX_REGISTER_CODE_PHONE + codeValidationDTO.getEmailOrPhone());
                    str = NotificationConstantUtils.REDIS_PREFIX_REGISTER_CODE_PHONE;
                    break;
                default:
                    throw new ParameterVerificationException("场景id错误！");
            }
        } else {
            throw new ParameterVerificationException("场景id错误！");
        }
        if (code == null) {
            throw new RRException("验证码过期，请重新发送");
        }
        if (!codeValidationDTO.getCode().equals(code)) {
            throw new RRException("验证码错误");
        }
        redisService.remove(str + codeValidationDTO.getEmailOrPhone());
        return Result.ok();
    }

    @Override
    public Result sendEmailCode(String email, Integer emailSceneId) {
        String randNum1 = CommonUtils.getRandomNum(emailVerificationCodeLength);
        switch (emailSceneId) {
            case ConstantUtils.EMAIL_VERIFICATION_CODE_SCENE_AT_LOGIN:
                String content = emailVerificationCodeSceneContentAtLogin.replace("{1}", randNum1).replace("{2}", emailVerificationCodeExpire / 60 + "");
                try {
                    SendEmailBy163.sendMessage(email, emailVerificationCodeOwnName, emailVerificationCodeSubjectAtLogin, content, SendEmailBy163.MessageRecipientTypeTO);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("error: {}", e.getMessage(), e);
                    throw new RRException("请联系管理员：" + e.getMessage());
                }
                redisService.set(ConstantUtils.EMAIL_VERIFICATION_CODE_SCENE_AT_LOGIN + email, randNum1, emailVerificationCodeExpire);
                return Result.ok();
            case ConstantUtils.EMAIL_VERIFICATION_CODE_SCENE_AT_REGISTER:
                String content1 = emailVerificationCodeSceneContentAtRegister.replace("{1}", randNum1).replace("{2}", emailVerificationCodeExpire / 60 + "");
                try {
                    SendEmailBy163.sendMessage(email, emailVerificationCodeOwnName, emailVerificationCodeSubjectAtRegister, content1, SendEmailBy163.MessageRecipientTypeTO);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("error: {}", e.getMessage(), e);
                    throw new RRException("请联系管理员：" + e.getMessage());
                }
                redisService.set(ConstantUtils.EMAIL_VERIFICATION_CODE_SCENE_AT_REGISTER + email, randNum1, emailVerificationCodeExpire);
                return Result.ok();
            default:
        }
        return Result.error(ExceptionEnum._400.setMessage("参数错误"));
    }

}
