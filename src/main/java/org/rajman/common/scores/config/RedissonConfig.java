package org.rajman.common.scores.config;

import org.rajman.common.scores.model.RedissonProperties;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@ComponentScan(basePackages = "org.rajman.common.scores.config")
public class RedissonConfig {

    @Bean
    @ConditionalOnMissingBean
    public RedissonClient Redisson(RedissonProperties redissonProperties) {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec());
        if (redissonProperties.isCluster()) {
            config.useClusterServers()
                    .setPassword(redissonProperties.getPassword())
                     .setNodeAddresses(Arrays.asList(redissonProperties.getAddress().split(",")));
        } else {
            config.useSingleServer().setAddress(redissonProperties.getAddress());
            if (redissonProperties.getPassword() != null)
                config.useSingleServer().setPassword(redissonProperties.getPassword());
        }
        return org.redisson.Redisson.create(config);
    }

    @Bean
    @ConfigurationProperties(prefix = "scores.redis")
    public RedissonProperties redissonProperty() {
        return new RedissonProperties();
    }
}
