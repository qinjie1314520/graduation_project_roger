package com.authority.controller.login;

import com.authority.entity.User;
import com.authority.mapper.UserMapper;
import com.common.pojo.RRException;
import com.common.pojo.Result;
import com.common.utils.JwtUtils;
import com.common.utils.PBKDF2Util;
import com.common.utils.constant.AuthorityConstantUtils;
import com.common.utils.constant.ConstantUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
  * 用户密码登陆时处理器
  * @Author qinjie
  * @CreateDate 2020-11-02 22:36
 */
@Component
public class LoginUsernameAndPasswordHandler implements LoginHandler  {
    @Override
    public Integer getLoginType() {
        return AuthorityConstantUtils.LOGIN_TYPE_USERNAME;
    }
    @Resource
    private UserMapper userMapper;
    @Value("${jwt.secret}")
    private String jwtSercet;
    @Value("${jwt.expire}")
    private Long jwtExpire;
    @Value("${jwt.iss}")
    private String jwtIss;
    @Override
    public Result handleLogin(String username, String password) {
        User user = userMapper.selUserOneByAccount(username);
        if(user==null){
            throw new RRException("用户不存在");
        }
        if(!PBKDF2Util.authenticate(password,user.getUPassword(),user.getUSalt())){
            throw new RRException("密码错误！");
        }
        //构建jwt需要保存的信息
        Map<String, Object> jwtMap = new HashMap<>(4);
        jwtMap.put(ConstantUtils.JWT_CLAIMS_FIELD_USERNAME, user.getUUsername());
        jwtMap.put(ConstantUtils.JWT_CLAIMS_FIELD_ID, user.getUId());
        jwtMap.put(ConstantUtils.JWT_CLAIMS_FIELD_ROLE_IDS, user.getURoleIds());
        return Result.ok(JwtUtils.generateJwtToken(jwtSercet, jwtExpire, jwtIss, user.getUUsername(), jwtMap));
    }
}
