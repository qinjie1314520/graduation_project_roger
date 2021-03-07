//package com.user.center.security;
//
//
//
//import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.List;
///**
//  * 自定义判断是否有权限访问该地址
//  *
//  * @author: qinjie
// **/
//@Component("rbacService")
//public class RbacServiceImpl  {
//    private AntPathMatcher antPathMatcher = new AntPathMatcher();
//    private List<String> permissionList = new ArrayList<String>();
//    @PostConstruct
//    void init(){
//        permissionList.add("/agency/swagger-ui.html/**");
//        permissionList.add("/agency/swagger-resources/**");
//        permissionList.add("/agency/webjars/**");
//        permissionList.add("/agency/health/status");
//        permissionList.add("/agency/chat/**");
//        permissionList.add("/agency/v2/**");
//        permissionList.add("/swagger-ui.html/**");
//        permissionList.add("/swagger-resources/**");
//        permissionList.add("/webjars/**");
//        permissionList.add("/chat/**");
//        permissionList.add("/v2/**");
//        permissionList.add("/agency/login");
//    }
//    public boolean hasPermission(HttpServletRequest request, Authentication authentication) throws AuthenticationCredentialsNotFoundException {
////        for(int i = 0; i < permissionList.size(); i++){
////            if(antPathMatcher.match(permissionList.get(i), request.getRequestURI())){
////                return true;
////            }
////        }
//        return true;
//    }
//}
