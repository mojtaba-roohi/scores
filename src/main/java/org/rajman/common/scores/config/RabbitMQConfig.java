package org.rajman.common.scores.config;

import org.rajman.common.scores.model.RabbitMQProperties;
import org.rajman.common.scores.model.RedissonProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfig {


    @Bean
    public ConnectionFactory connectionFactory(RabbitMQProperties rabbitMQProperties) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername(rabbitMQProperties.getUsername());
        connectionFactory.setPassword(rabbitMQProperties.getPassword());
        connectionFactory.setPort(rabbitMQProperties.getPort());
      connectionFactory.setHost(rabbitMQProperties.getHost());
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;
    }
    @Bean
    @ConfigurationProperties(prefix = "scores.rabbitmq")
    public RabbitMQProperties rabbitMQProperties() {
        return new RabbitMQProperties();
    }
}
