package com.authority.redis;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
  * redis的配置类
  *
  * @author: qinjie
 **/
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {

    //设置redisTemplate
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
//        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
//        redisTemplate.setKeySerializer(stringRedisSerializer);
//        redisTemplate.setValueSerializer(jsonRedisSerializer);
//        redisTemplate.setHashKeySerializer(stringRedisSerializer);
//        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
//        redisTemplate.setEnableTransactionSupport(false);
//
//        return redisTemplate;
//    }
//    @Autowired
//    private RedisConnectionFactory factory;
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        redisTemplate.setConnectionFactory(factory);
//        return redisTemplate;
//    }
//
        @Bean
        public RedisTemplate<String, Object> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
            template.setConnectionFactory(redisConnectionFactory);
            return template;
        }
//    @Resource
//    private LettuceConnectionFactory myLettuceConnectionFactory;
//    @Bean
//    public RedisTemplate<String, Serializable> redisTemplate() {
//        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setConnectionFactory(myLettuceConnectionFactory);
//        return template;
//    }
}
