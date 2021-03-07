package com.controller.validator;

import java.lang.annotation.*;

/**
  * DTO校验
  *
  * @author: qinjie
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DTOValidatorAnnotation {

}
