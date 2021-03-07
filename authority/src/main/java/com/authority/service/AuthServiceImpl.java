package com.authority.service;

import com.authority.controller.login.LoginFactory;
import com.authority.controller.login.LoginHandler;
import com.authority.mapper.GraduationUserRoleMapper;
import com.authority.mapper.UserMapper;
import com.authority.redis.RedisService;
import com.authority.utils.SendEmailBy163;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.common.pojo.BinarySearchAuthorityTree;
import com.common.pojo.RRException;
import com.common.pojo.Result;
import com.common.utils.CommonUtils;
import com.common.utils.PBKDF2Util;
import com.common.utils.constant.ConstantUtils;
import com.controller.controller.AuthorityAuthControllerInterface;
import com.controller.dto.LoginDTO;
import com.controller.dto.RegisterDTO;
import com.model.entity.GraduationAuthority;
import com.model.entity.GraduationUser;
import com.model.entity.GraduationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthorityAuthControllerInterface {

    @Resource
    private UserMapper userMapper;

    @Resource
    private GraduationUserRoleMapper graduationUserRoleMapper;


    @Value("${jwt.secret}")
    private String jwtSercet;
    @Value("${jwt.expire}")
    private Long jwtExpire;
    @Value("${jwt.iss}")
    private String jwtIss;
    @Override
    public Result selPrivateKey() {
        return Result.ok(jwtSercet);
    }



    /**
     * 邮件显示的发件公司
     */
    @Value("${email.verification.code.own.name}")
    private String emailVerificationCodeOwnName;
    /**
     * 邮箱验证码长度
     */
    @Value("${email.verification.code.length}")
    private Integer emailVerificationCodeLength;
    /**
     * 邮箱验证码场景——登录验证码内容
     */
    @Value("${email.verification.code.scene.content.at.login}")
    private String emailVerificationCodeSceneContentAtLogin;
    /**
     * 邮箱验证码场景——注册验证码内容
     */
    @Value("${email.verification.code.scene.content.at.register}")
    private String emailVerificationCodeSceneContentAtRegister;

    /**
     * 邮箱验证码有效期
     */
    @Value("${email.verification.code.expire}")
    private Long emailVerificationCodeExpire;


    @Autowired
    private RedisService redisService;
    @Autowired
    private LoginFactory loginFactory;
    @Override
    public Result login(LoginDTO loginDTO) {
        LoginHandler loginHandler = loginFactory.getLoginHandler(loginDTO.getLoginType());
        return loginHandler.handleLogin(loginDTO.getUsername(), loginDTO.getPassword());
//        switch (loginDTO.getLoginType()){
//            case AuthorityConstantUtils.LOGIN_TYPE_EMAIL:
//                //构建验证码校验参数
//                CodeValidationDTO codeValidationDTO = new CodeValidationDTO();
//                codeValidationDTO.setCode(loginDTO.getPassword());
//                codeValidationDTO.setEmailOrPhone(loginDTO.getUsername());
//                Result result = notificationFeignService.codeEmailValidate(codeValidationDTO);
//                if(result.getCode()==200){
//                    //成功，验证
//                    //查询数据库，生成jwt返回用户
//                    User user1 = userMapper.selUserOneByEmail(loginDTO.getUsername());
//                    if(user1 == null){
//                        throw new RRException("不存在用户");
//                    }
//                    //构建jwt需要保存的信息
//                    Map<String, Object> jwtMap = new HashMap<String,Object>();
//                    jwtMap.put(ConstantUtils.JWT_CLAIMS_FIELD_USERNAME, user1.getUUsername());
//                    jwtMap.put(ConstantUtils.JWT_CLAIMS_FIELD_ID, user1.getUId());
//                    jwtMap.put(ConstantUtils.JWT_CLAIMS_FIELD_ROLE_IDS, user1.getURoleIds());
//                    return Result.ok(JwtUtils.generateJwtToken(jwtSercet, jwtExpire, jwtIss, user1.getUUsername(), jwtMap));
//                }else{
//                    return result;
//                }
//            case AuthorityConstantUtils.LOGIN_TYPE_PHONE:
//                break;
//            case AuthorityConstantUtils.LOGIN_TYPE_USERNAME:
//                break;
//            default:
//                throw new ParameterVerificationException("参数错误！");
//        }
    }

    @Override
    public Result selLoginCode(String phoneOrEmail) {
        //不存在就抛出错误
            GraduationUser user = new GraduationUser();
        if (CommonUtils.isPhone(phoneOrEmail)) {
            //标识手机
            //手机是否存在
            user.setPhone(phoneOrEmail);
            if(userMapper.selectOne(new QueryWrapper<>(user))==null){
                throw new RRException("不存在用户");
            }
            return null;

        } else {
            //标识邮箱
            //邮箱是否存在
            user.setEmail(phoneOrEmail);
            if(userMapper.selectOne(new QueryWrapper<>(user))==null){
                throw new RRException("不存在用户");
            }
            return null;
        }
    }

    @Override
    public Result<BinarySearchAuthorityTree> selRolesAuthorities(Long roleId) {
        BinarySearchAuthorityTree binarySearchAuthorityTree = new BinarySearchAuthorityTree();
        //查询出所有的权限
        List<GraduationAuthority> authorities = userMapper.selRoleAuthorities(roleId);

        if(authorities!=null  && authorities.size() !=0 ){
            //固件权限树
            authorities.forEach(x->{
            binarySearchAuthorityTree.insert(x.getAUrl());
            });
        }
        if(!binarySearchAuthorityTree.isEmpty()){
            return Result.ok(binarySearchAuthorityTree);
        }
        throw new RRException("角色异常，请联系管理员");
    }

    @Override
    @Transactional
    public Result register(RegisterDTO registerDTO) {
        Object code = redisService.get(ConstantUtils.EMAIL_VERIFICATION_CODE_SCENE_AT_REGISTER + registerDTO.getEmail());
        if(code==null){
            throw new RRException("验证码失效，请重新发送！");
        }
        if(!registerDTO.getCode().equals(code)) {
            throw new RRException("验证码错误！");
        }


        GraduationUser query = new GraduationUser();
        query.setUsername(registerDTO.getUsername());
        GraduationUser user = userMapper.selectOne(new QueryWrapper<>(query));
        //校验用户名是否存在
        if (user != null) {
            throw new RRException("用户名已被使用，请修改后再次提交！");
        }
        //校验邮箱是否存在
        query.setUsername(null);
        query.setEmail(registerDTO.getEmail());
        user = userMapper.selectOne(new QueryWrapper<>(query));
        if (user != null) {
            throw new RRException("邮箱已被使用，请修改后再次提交！");
        }
        //密码进行加密处理
        try {
            String salt = PBKDF2Util.generateSalt();
            String newPassword = PBKDF2Util.getEncryptedPassword(registerDTO.getPassword(), salt);
            GraduationUser tbUser1 = new GraduationUser();
            tbUser1.setUsername(registerDTO.getUsername());
            tbUser1.setAccount(registerDTO.getUsername());
            tbUser1.setEmail(registerDTO.getEmail());
            tbUser1.setPassword(newPassword);
            tbUser1.setSalt(salt);
            userMapper.insert(tbUser1);

            GraduationUserRole graduationUserRole = new GraduationUserRole();
            graduationUserRole.setRoleId(GraduationUserRole.ROLE_ID_Normal_User);
            graduationUserRole.setUserId(tbUser1.getId());
            graduationUserRoleMapper.insert(graduationUserRole);
        } catch (NoSuchAlgorithmException e) {
            throw new RRException("加密错误，请联系管理员");
        }
        redisService.remove(ConstantUtils.EMAIL_VERIFICATION_CODE_SCENE_AT_REGISTER + registerDTO.getEmail());
        return Result.ok();
    }

    @Override
    public Result selRegisterCode(String email) {
        if( CommonUtils.isEmail(email)){
            String randomNum = CommonUtils.getRandomNum(emailVerificationCodeLength);
            String content1 = emailVerificationCodeSceneContentAtRegister.replace("{1}", randomNum).replace("{2}", emailVerificationCodeExpire / 60 + "分");
            try {
                SendEmailBy163.sendMessage(email, emailVerificationCodeOwnName, "验证码", content1, SendEmailBy163.MessageRecipientTypeTO);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RRException("请联系管理员：" + e.getMessage());
            }
            redisService.set(ConstantUtils.EMAIL_VERIFICATION_CODE_SCENE_AT_REGISTER + email, randomNum, emailVerificationCodeExpire);
            return Result.ok();
        }else{
            throw new RRException("邮箱异常！");
        }
    }

    public static void main(String args[]) throws NoSuchAlgorithmException {
        String salt = PBKDF2Util.generateSalt();
        String pass = PBKDF2Util.getEncryptedPassword("roger", salt);
        System.out.println(salt);
        System.out.println(pass);
    }
}
