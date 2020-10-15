package com.simple.core.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisClient {
       public static ValueOperations<String, Object> operatorInstance;
       public static RedisTemplate<String, Object> templateInstance;

        @Bean
        JedisConnectionFactory connectionFactory() {
            return new JedisConnectionFactory();
        }

        @Bean
        ValueOperations<String, String> strOperations(RedisTemplate<String, String> redisTemplate) {
            return redisTemplate.opsForValue();
        }

        @Bean
        RedisTemplate<String, Integer> intRedisTemplate(JedisConnectionFactory connectionFactory) {
            RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<String, Integer>();
            redisTemplate.setConnectionFactory(connectionFactory);
            return redisTemplate;
        }

        @Bean
        ValueOperations<String, Integer> intOperations(RedisTemplate<String, Integer> redisTemplate) {
            return redisTemplate.opsForValue();
        }

        @Bean
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(ObjectMapper objectMapper) {
            Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                    Object.class);
            jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
            return jackson2JsonRedisSerializer;
        }

        @Bean
        RedisTemplate<String, Object> objRedisTemplate(JedisConnectionFactory connectionFactory,
                                                       Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer) {
            RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
            redisTemplate.setConnectionFactory(connectionFactory);
            redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
            StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(stringRedisSerializer);
            redisTemplate.setHashKeySerializer(stringRedisSerializer);
            RedisClient.templateInstance = redisTemplate;
            return redisTemplate;
        }

        @Bean
        ValueOperations<String, Object> objOperations(RedisTemplate<String, Object> redisTemplate) {
            RedisClient.operatorInstance = redisTemplate.opsForValue();
            return RedisClient.operatorInstance;
        }

    }
