package com.authority.controller.login;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoginFactory implements InitializingBean, ApplicationContextAware {
    private static final
    Map<Integer, LoginHandler> FORM_LOGIN_HANDLER_MAP = new HashMap<>(8);

    private ApplicationContext appContext;

    /**
     * 根据提交类型获取对应的处理器
     *
     * @param type 提交类型
     * @return 提交类型对应的处理器
     */
    public LoginHandler getLoginHandler(Integer type) {
        return FORM_LOGIN_HANDLER_MAP.get(type);
    }

    @Override
    public void afterPropertiesSet() {
        // 将 Spring 容器中所有的 FormSubmitHandler 注册到 FORM_SUBMIT_HANDLER_MAP
        appContext.getBeansOfType(LoginHandler.class).values().forEach(x -> FORM_LOGIN_HANDLER_MAP.put(x.getLoginType(), x));
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        appContext = applicationContext;
    }
}
