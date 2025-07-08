package com.goldeelox.mtg.MTGDeckBuilder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.net.URI;

@Configuration
public class RedisFactoryConfig {

    @Value("${spring.redis.url}")
    private String redisUrl;

    @Bean(name = "directLettuceFactory")
    public RedisConnectionFactory directLettuceFactory() {
        URI uri = URI.create(redisUrl);

        String host = uri.getHost();
        int port = uri.getPort();
        String userInfo = uri.getUserInfo(); // "default:password"
        String[] userPass = userInfo.split(":", 2);
        String username = userPass[0];
        String password = userPass[1];

        RedisStandaloneConfiguration standalone = new RedisStandaloneConfiguration(host, port);
        standalone.setUsername(username);
        standalone.setPassword(RedisPassword.of(password));

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .useSsl()
                .build();

        return new LettuceConnectionFactory(standalone, clientConfig);
    }
}