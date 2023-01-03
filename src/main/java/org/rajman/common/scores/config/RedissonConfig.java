package org.rajman.common.scores.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.rajman.common.scores.config")
public class RedissonConfig {

    @Autowired
    RedissonProperties redissonProperties;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient RedissonConfig() {
        Config config = new Config();
        if (redissonProperties.isCluster()) {
            config.useClusterServers()
                    .setPassword(redissonProperties.getPassword());
//                     .setNodeAddresses();
        } else {
            config.useSingleServer().setAddress(redissonProperties.getAddress());
            if (redissonProperties.getPassword() != null)
                config.useSingleServer().setPassword(redissonProperties.getPassword());
        }
        return Redisson.create(config);
    }
}
