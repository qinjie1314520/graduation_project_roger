package com.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.common.pojo.ExceptionEnum;
import com.common.pojo.Result;
import com.common.utils.JwtUtils;
import com.common.utils.constant.ConstantUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.common.utils.constant.ConstantUtils.JWT_CLAIMS_FIELD_EXPIRE;

/**
 * JWT过滤器, 进行初次的权限校验，包括jwt是否存在，接口是否开放
 *
 * @author roger
 * @since 2020/11/6 15:20
 */
@Component
public class JWTFilter implements GlobalFilter, Ordered {
    @Value("${open.url}")
    private List<String> openUrl;
    @Value("${jwt.secret}")
    private String jwtSecret;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //开放的接口放通
        for (String x : openUrl) {
            if (antPathMatcher.match(x, exchange.getRequest().getURI().getPath())) {
                return chain.filter(exchange);
            }
        }

        List<String> authorization = exchange.getRequest().getHeaders().get("Authorization");
        String jwt = authorization != null && authorization.size() != 0 ? authorization.get(0) : null;

        if (jwt == null || jwt.isEmpty()) {
            // 返回未登录得提示信息
            return exchange.getResponse().writeWith(Flux.just(exchange.getResponse()
                    .bufferFactory().wrap(JSONObject.toJSONString(Result.error(ExceptionEnum._401_1)).getBytes())));
        }
        Claims cl = JwtUtils.getClaimsFromJwt(jwtSecret, jwt);
        if(cl==null){
            // 返回未登录得提示信息
            return exchange.getResponse().writeWith(Flux.just(exchange.getResponse()
                    .bufferFactory().wrap(JSONObject.toJSONString(Result.error(ExceptionEnum._401)).getBytes())));

        }
        //jwt到期时间
        Long expire = (Long)cl.get(ConstantUtils.JWT_CLAIMS_FIELD_EXPIRE);

        if(expire != null && expire > System.currentTimeMillis()){
            //没到期就继续向下过滤器
            return chain.filter(exchange);
        }else{
            //jwt到期
            // 返回未登录得提示信息
            return exchange.getResponse().writeWith(Flux.just(exchange.getResponse()
                    .bufferFactory().wrap(JSONObject.toJSONString(Result.error(ExceptionEnum._401_2)).getBytes())));

        }
    }

    @Override
    public int getOrder() {
        return -100;
    }

}
