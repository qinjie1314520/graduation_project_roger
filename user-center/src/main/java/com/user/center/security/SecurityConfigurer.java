//package com.user.center.security;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
//
///**
//  * ssecurity的配置
//  *
//  * @author: qinjie
// **/
//@Configuration
//@EnableWebSecurity
//public class SecurityConfigurer extends WebSecurityConfigurerAdapter{
//
//    @Autowired
//    private LindJwtAuthenticationFilter lindTokenAuthenticationFilter;
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(null);
//    }
//
//    /**
//     * 设置了登录页面，而且登录页面任何人都可以访问，然后设置了登录失败地址，也设置了注销请求，注销请求也是任何人都可以访问的。
//     * permitAll表示该请求任何人都可以访问，.anyRequest().authenticated(),表示其他的请求都必须要有权限认证。
//     * */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests()
//                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .anyRequest().access("@rbacService.hasPermission(request,authentication)");
//        ; //关闭CSRF
//        http.addFilterAt(lindTokenAuthenticationFilter, FilterSecurityInterceptor.class);
//        http.headers().cacheControl();//禁用缓存
//    }
//}
