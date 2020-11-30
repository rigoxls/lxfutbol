package com.lxfutbol.transformRest.conf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lxfutbol.transformRest.repository.ProviderTemplateEntity;
 
@Configuration
public class RedisConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
    	JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName("redis-service");
        factory.setPort(6379);
        return factory;        
    }

    @Bean
    public RedisTemplate<String, ProviderTemplateEntity> redisTemplate() {
        final RedisTemplate<String, ProviderTemplateEntity> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        Jackson2JsonRedisSerializer<ProviderTemplateEntity> values = new Jackson2JsonRedisSerializer<>(ProviderTemplateEntity.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        values.setObjectMapper(objectMapper);
        template.setHashValueSerializer(values);
        return template;
    }    
}