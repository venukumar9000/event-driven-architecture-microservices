package com.example.order_service.publisher;

import com.example.order_service.dto.Order;
import com.example.order_service.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.order.routing.key}")
    private String orderRoutingKey;

    @Value("${rabbitmq.email.routing.key}")
    private String emailRoutingKey;


    @Autowired
    public RabbitTemplate rabbitTemplate;

    public void sendMessage(OrderEvent orderEvent){
        LOGGER.info(String.format("Message sent ->%s",orderEvent));

        //sent an event to order queue
        rabbitTemplate.convertAndSend(exchange,orderRoutingKey,orderEvent);
        //sent an event to email queue
        rabbitTemplate.convertAndSend(exchange,emailRoutingKey,orderEvent);


    }

}
