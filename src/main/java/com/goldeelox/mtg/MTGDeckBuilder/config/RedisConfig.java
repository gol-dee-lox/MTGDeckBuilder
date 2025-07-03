package com.goldeelox.mtg.MTGDeckBuilder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.*;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;
    
    @Value("${spring.redis.username}")
    private String username;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration(host, port);
        standaloneConfig.setUsername(username);
        standaloneConfig.setPassword(RedisPassword.of(password));

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
            .useSsl()  // ⚠️ Enables SSL/TLS
            .build();

        return new LettuceConnectionFactory(standaloneConfig, clientConfig);
    }
}