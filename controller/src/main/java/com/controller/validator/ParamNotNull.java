package com.controller.validator;

import java.lang.annotation.*;

/**
  * ParamNotNull校验，判断Integer，String不为null或者""
  * 
  * @author: qinjie
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Inherited
public @interface ParamNotNull {
}
