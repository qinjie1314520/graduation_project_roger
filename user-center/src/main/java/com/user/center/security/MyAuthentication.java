//package com.user.center.security;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//import java.util.List;
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class MyAuthentication implements Authentication {
//    private String userId;
//    private String username;
//    private List<String> roleIds;
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public Object getCredentials() {
//        return null;
//    }
//
//    @Override
//    public Object getDetails() {
//        return null;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return null;
//    }
//
//    @Override
//    public boolean isAuthenticated() {
//        return false;
//    }
//
//    @Override
//    public void setAuthenticated(boolean b) throws IllegalArgumentException {
//
//    }
//
//    @Override
//    public String getName() {
//        return username;
//    }
//}
