package com.example.stock_service.consumer;

import com.example.stock_service.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactoryFriend;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.order.name}"})
    private void orderConsumer(OrderEvent orderEvent){
       LOGGER.info(String.format("Received order =>%s",orderEvent.toString()));
   }
}
