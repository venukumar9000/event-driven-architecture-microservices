package com.example.order_service.controller;

import com.example.order_service.dto.Order;
import com.example.order_service.dto.OrderEvent;
import com.example.order_service.publisher.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.server.UID;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class OrderController {

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order){

        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent= new OrderEvent();
        orderEvent.setStatus("pending");
        orderEvent.setMessage("order is in pending");
        orderEvent.setOrder(order);


        orderProducer.sendMessage(orderEvent);

        return "order sent to rabbitmq....";
    }
}
