package org.rajman.common.scores.config;

import org.rajman.common.scores.model.RabbitMQProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.rajman.common.scores.config")
public class RabbitMQConfig {


    @Bean
    @ConditionalOnMissingBean
    public ConnectionFactory connectionFactory(RabbitMQProperties rabbitMQProperties) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername(rabbitMQProperties.getUsername());
        connectionFactory.setPassword(rabbitMQProperties.getPassword());
        connectionFactory.setPort(rabbitMQProperties.getPort());
        connectionFactory.setHost(rabbitMQProperties.getHost());
        return connectionFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cachingConnectionFactory) {
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        return template;
    }

    @Bean
    @ConfigurationProperties(prefix = "scores.rabbitmq")
    public RabbitMQProperties rabbitMQProperties() {
        return new RabbitMQProperties();
    }
}
