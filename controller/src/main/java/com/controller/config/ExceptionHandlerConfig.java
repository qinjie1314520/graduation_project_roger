package com.controller.config;

import com.common.pojo.ExceptionEnum;
import com.common.pojo.ParameterVerificationException;
import com.common.pojo.RRException;
import com.common.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.List;

/**
 * 自定义异常处理
 *
 * @author: qinjie
 **/
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerConfig {


    //可预见性异常
    //自定义异常,服务器错误
    @ExceptionHandler(RRException.class)
    public Result handlerRRException(RRException e) {
        log.error("error: {}", e.getMessage(), e);
        return Result.error(ExceptionEnum._500.setMessage(e.getMessage()));
    }
    //可预见性异常
    //DTO参数校验错误
    @ExceptionHandler(ParameterVerificationException.class)
    public Result invalidDTOException(ParameterVerificationException e){
        log.error("error: {}", e.getMessage(), e);
        return Result.error(ExceptionEnum._400.setMessage(e.getMessage()));
    }
    //可预见性异常
    //DTO参数校验错误
    @ExceptionHandler(ValidationException.class)
    public Result invalidValidationException(ValidationException e){
        log.error("error: {}", e.getMessage(), e);
        return Result.error(ExceptionEnum._400.setMessage(e.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("error: {}", e.getMessage(), e);
        BindingResult result = e.getBindingResult();
        String errorMessage = "";
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            FieldError fieldError = (FieldError) errors.get(0);
            errorMessage = fieldError.getDefaultMessage();
        }
        return Result.error(ExceptionEnum._400.setMessage(errorMessage));
    }
    //没有认证信息
    @ExceptionHandler(MissingRequestHeaderException.class)
    public Result missingRequestHeaderException(MissingRequestHeaderException e){
        log.error("error: {}", e.getMessage(), e);
        return Result.error(ExceptionEnum._401_1);
    }
    //未知异常
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e) {
        System.out.println("\n\n\n"+e.getClass());
        log.error("error: {}", e.getMessage(), e);
        return Result.error(ExceptionEnum._500.setMessage("未知错误，请联系管理员"));
    }

 //未知异常
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handlerException(HttpRequestMethodNotSupportedException e) {
        log.error("error: {}", e.getMessage(), e);
        return Result.error(ExceptionEnum._405);
    }





}
