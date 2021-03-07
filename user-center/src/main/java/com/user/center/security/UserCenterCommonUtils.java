//package com.user.center.security;
//
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
//  * 简单的工具类
//  *
//  * @author: qinjie
// **/
//public class UserCenterCommonUtils {
//    /**
//     *
//     * 功能描述: 得到头部保存的信息，指定key信息
//     *
//     * @return: java.lang.String
//     * @auther: 秦杰
//     * @date: 2019/7/27 16:12
//     */
//    public static String getHeadKey( String key) {
//        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        Map<String, String> map = new HashMap<String, String>();
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key1 = (String) headerNames.nextElement();
//            String value = request.getHeader(key1);
//            map.put(key1, value);
//        }
//        return map.get(key);
//    }
//}
