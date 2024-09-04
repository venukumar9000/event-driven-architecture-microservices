package com.example.order_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.queue.order.name}")
    private String orderQueue;

    @Value("${rabbitmq.queue.email.name}")
    private String emailQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.order.routing.key}")
    private String orderRoutingKey;

    @Value("${rabbitmq.email.routing.key}")
    private String emailRoutingKey;

    @Bean
    public Queue orderQueue(){
        return new Queue(orderQueue);
    }

    @Bean
    public Queue emailQueue(){
        return new Queue(emailQueue);
    }

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(orderQueue())
                .to(exchange())
                .with(orderRoutingKey);
    }
    @Bean
    public Binding emailbinding(){
        return BindingBuilder
                .bind(emailQueue())
                .to(exchange())
                .with(emailRoutingKey);
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
         rabbitTemplate.setMessageConverter(messageConverter());
         return rabbitTemplate;
    }
}
