package com.controller.validator;

import com.common.pojo.ParameterVerificationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
  * 基于切面的拦截器，拦截指定的方法
  *
  * @author: qinjie
 **/
@Aspect
public class AopValidator {
    //    private static Logger logger = LogManager.getLogger(RedisAop.class);
    @Pointcut("@annotation(com.controller.validator.DTOValidatorAnnotation)")
    public void InterceptController(){}

    //DTO校验
    @Around(value = "InterceptController()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        Object obj[] = proceedingJoinPoint.getArgs();
        String[] paramNames = ((CodeSignature) proceedingJoinPoint
                .getSignature()).getParameterNames();
        //验证第一个对象中参数
        DTOValidator.validate(obj[0]);
        //返回执行目标方法，并且把转换修改后的参数值传递下去
        return proceedingJoinPoint.proceed(obj);
    }

    //ParamNotNull校验
    @Pointcut("@annotation(com.controller.validator.ParamNotNull)")
    public void InterceptController1(){}
    @Around("InterceptController1()")
    public Object Around1(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] params = joinPoint.getArgs();
        if(params.length == 0){
            return joinPoint.proceed();
        }

        //获取方法，此处可将signature强转为MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //变量类型
        Class[] patameterTypes  = signature.getParameterTypes();
        String[] patameterNames = signature.getParameterNames();
        //参数注解，1维是参数，2维是注解
        Annotation[][] annotations = method.getParameterAnnotations();
        for(int i = 0;i < annotations.length; i++){
            if(annotations[i].length != 0){
                //代表存在注解
                if(patameterTypes[i] == Integer.class){
                    //代表参数中是Integer，需要判断不为null
                    if(params[i]==null){
                        throw new ParameterVerificationException(patameterNames[i]+"参数不能为空");
                    }
                }
                else if(patameterTypes[i] == String.class){
                    //代表参数中是String，需要判断不为null,并且长度不为0
                    if(params[i]==null || (params[i]+"").length()==0){
                        throw new ParameterVerificationException(patameterNames[i]+"参数不能为空(长度不能为0)");
                    }
                }
            }
        }
        return joinPoint.proceed();
    }
}
