package org.rajman.common.scores.config;

import org.rajman.common.scores.model.RedissonProperty;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.rajman.common.scores.config")
public class Redisson {

    @Bean
    @ConditionalOnMissingBean
    public RedissonClient Redisson(RedissonProperty redissonProperty){
        Config config=new Config();
        System.out.println("redis-scores-connect");
        if(redissonProperty.isCluster()){
            config.useClusterServers()
                    .setPassword(redissonProperty.getPassword());
//                     .setNodeAddresses();
        } else{
            config.useSingleServer().setAddress(redissonProperty.getAddress());
            if( redissonProperty.getPassword()!=null)
                config.useSingleServer().setPassword (redissonProperty.getPassword());
        }
        return org.redisson.Redisson.create(config);
    }

    @Bean
    @ConfigurationProperties(prefix = "scores-redis")
    public RedissonProperty redissonProperty() {
        return new RedissonProperty();
    }
}
