package com.notification.controller;

import com.common.pojo.ParameterVerificationException;
import com.common.pojo.Result;
import com.common.utils.CommonUtils;
import com.controller.controller.NotificationEmailControllerInterface;
import com.controller.dto.CodeValidationDTO;
import com.controller.validator.DTOValidatorAnnotation;
import com.notification.service.EmailServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;
import java.util.List;

/**
 * 邮箱通知相关接口
 * @author roger
 * @since  2020/11/3 13:17
 */
@RestController
public class EmailController implements NotificationEmailControllerInterface {
    private EmailServiceImpl emailService;
    public EmailController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @Override
    @RequestMapping(value = "/notification/validateCode",method = RequestMethod.GET)
    @DTOValidatorAnnotation
    public Result codeEmailValidate(CodeValidationDTO codeValidationDTO) {
        //简单的校验邮箱
        if(!CommonUtils.isEmail(codeValidationDTO.getEmailOrPhone())){
            throw new ParameterVerificationException("请求参数错误！");
        }
        return emailService.codeEmailValidate(codeValidationDTO);
    }

    @Override
    @RequestMapping(value = "/notification/sendEmailCode",method = RequestMethod.GET)
    public Result sendEmailCode(String email, Integer emailSceneId) {
        if(!CommonUtils.isEmail(email)  || emailSceneId ==null || emailSceneId < 0){
            throw new ParameterVerificationException("参数错误");
        }
        return emailService.sendEmailCode(email,emailSceneId);
    }
}
