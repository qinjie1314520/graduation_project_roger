//package com.user.center.security;
//
//import com.alibaba.fastjson.JSONObject;
//import com.common.pojo.ExceptionEnum;
//import com.common.pojo.RRException;
//import com.common.utils.CommonUtils;
//import com.common.utils.JwtUtils;
//import com.common.utils.constant.ConstantUtils;
//import io.jsonwebtoken.Claims;
//import org.apache.logging.log4j.util.Strings;
//import org.apache.tomcat.util.bcel.Const;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 自定义过滤器，添加进入security进行授权用户添加，并且处理异常
// *
// * @author: qinjie
// **/
//@Component
//public class LindJwtAuthenticationFilter extends OncePerRequestFilter {
//    /**
//     * jwt密钥
//     */
//    @Value("${jwt.secret}")
//    private String jwtSecret;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        try {
//            //得到redis中保存的用户认证信息
//            Object authorization = UserCenterCommonUtils.getHeadKey(ConstantUtils.FRONT_KEY_AUTHORIZATION);
//            //如果存在jwt信息
//            if (!Strings.isBlank("" + authorization)) {
//                //解析用户信息设置到上下问
//                Claims claim = JwtUtils.getClaimsFromJwt(jwtSecret, authorization + "");
//                if (claim == null) {
//                    throw new RRException(ExceptionEnum._401.getMessage(), ExceptionEnum._401.getCode());
//                }
//                //查看到期时间是否到期
//                Long expire = claim.get(ConstantUtils.JWT_CLAIMS_FIELD_EXPIRE, Long.class);
//                if (System.currentTimeMillis() >= expire) {
//                    throw new RRException(ExceptionEnum._401.getMessage(), ExceptionEnum._401.getCode());
//                }
//                MyAuthentication myAuthentication = new MyAuthentication();
//                myAuthentication.setUserId(claim.get(ConstantUtils.JWT_CLAIMS_FIELD_ID, String.class));
//                myAuthentication.setRoleIds(claim.get(ConstantUtils.JWT_CLAIMS_FIELD_ROLE_IDS, List.class));
//                myAuthentication.setUsername(claim.get(ConstantUtils.JWT_CLAIMS_FIELD_USERNAME, String.class));
//                SecurityContextHolder.getContext().setAuthentication(myAuthentication);
//            }
//            filterChain.doFilter(request, response);
//        } catch (RRException e) {
//            e.printStackTrace();
//            JSONObject json = new JSONObject();
//            json.put("code", e.getCode());
//            json.put("msg", e.getCode());
//            returnResult(response, json);
//        }
//    }
//
//    /**
//     * 功能描述: 直接返回前端json数据
//     *
//     * @param response HttpServletResponse
//     * @param json     JSONObject
//     * @return void
//     */
//    private void returnResult(HttpServletResponse response, JSONObject json) throws IOException {
//        response.setCharacterEncoding("utf-8");
//        response.setContentType("application/json; charset=utf-8");
//        PrintWriter writer = response.getWriter();
//        writer.write(json.toJSONString());
//    }
//}
