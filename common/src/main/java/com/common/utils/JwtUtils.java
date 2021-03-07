package com.common.utils;

import com.common.utils.constant.ConstantUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.common.utils.constant.ConstantUtils.JWT_CLAIMS_FIELD_EXPIRE;

public class JwtUtils {

    //私钥 / 生成签名的时候使用的秘钥secret，一般可以从本地配置文件中读取，切记这个秘钥不能外露，只在服务端使用，在任何场景都不应该流露出去。
    // 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
//    private final static String secret = "secretKey";

    // 过期时间（单位秒）/ 2小时
//    private final static Long access_token_expiration = 7200L;

    //jwt签发者
//    private final static String jwt_iss = "roger";

    //jwt所有人
//    private final static String subject = "roger";

    /**
      * 生成jwt密匙
      * @param secret 密匙
      * @param access_token_expiration 有效时长(SECOND)
      * @param jwt_iss 签发人
      * @param subject 面向人
      * @param claims 负载中保存的参数
      * @return java.lang.String
      */
    public static String generateJwtToken(String secret,Long access_token_expiration,String jwt_iss,String subject,Map<String,Object> claims){

        // 头部 map / Jwt的头部承载，第一部分
        // 可不设置 默认格式是{"alg":"HS256"}
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");


//        //载荷 map / Jwt的载荷，第二部分
//        Map<String,Object> claims = new HashMap<String,Object>();
//
//        //私有声明 / 自定义数据，根据业务需要添加
//        claims.put("id","123456");
//        claims.put("userName", "admin");

        //标准中注册的声明 (建议但不强制使用)
        //一旦写标准声明赋值之后，就会覆盖了那些标准的声明
        claims.put("iss", jwt_iss);
        Date date = new Date(System.currentTimeMillis() + access_token_expiration * 1000);
        claims.put(JWT_CLAIMS_FIELD_EXPIRE, date.getTime());

            /*	iss: jwt签发者
                sub: jwt所面向的用户
                aud: 接收jwt的一方
                exp: jwt的过期时间，这个过期时间必须要大于签发时间
                nbf: 定义在什么时间之前，该jwt都是不可用的.
                iat: jwt的签发时间
                jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
            */


        //下面就是在为payload添加各种标准声明和私有声明了
        return Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
                .setHeader(map)         // 头部信息
                .setClaims(claims)      // 载荷信息
                .setId(UUID.randomUUID().toString()) // 设置jti(JWT ID)：是JWT的唯一标识，从而回避重放攻击。
                .setIssuedAt(new Date())       // 设置iat: jwt的签发时间
                .setExpiration(date) // 设置exp：jwt过期时间
                .setSubject(subject)    //设置sub：代表这个jwt所面向的用户，所有人
                .signWith(ConstantUtils.SIGNATURE_ALGORITHM, secret)//设置签名：通过签名算法和秘钥生成签名
                .compact(); // 开始压缩为xxxxx.yyyyy.zzzzz 格式的jwt token
    }


    /**
     *  从jwt中获取 载荷 信息
     * @param jwt jwt
     * @param secret 密匙
     * @return
     */
    public static Claims getClaimsFromJwt(String secret,String jwt) {
        Claims claims = null;
        if(jwt==null){
            return null;
        }
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }
    /**
     * 通过jwt获得用户id
     * @param authentication jwt信息
     * @param secret 密钥
     * @return 用户id
     */
    public static Long getUserId(String secret,String authentication){
        //获得用户id
        Claims claimsFromJwt = JwtUtils.getClaimsFromJwt(secret, authentication);
        return claimsFromJwt==null?null:claimsFromJwt.get(ConstantUtils.JWT_CLAIMS_FIELD_ID, Long.class);
    }
}