package com.authority.config;

import com.common.pojo.ExceptionEnum;
import com.common.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

/**
 * 自定义异常处理
 *
 * @author: qinjie
 **/
@RestControllerAdvice
@Slf4j
public class AuthorityExceptionHandlerConfig {

}
