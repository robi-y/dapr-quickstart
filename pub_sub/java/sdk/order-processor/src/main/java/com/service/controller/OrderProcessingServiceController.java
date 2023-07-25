package com.service.controller;

import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Mono;
import java.util.Date;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;


@RestController
public class OrderProcessingServiceController {

    private static final Logger logger = LoggerFactory.getLogger(OrderProcessingServiceController.class);
    private final ApplicationEventPublisher publisher;

    public OrderProcessingServiceController(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Topic(name = "orders", pubsubName = "orderpubsub")
    @PostMapping(path = "/orders", consumes = MediaType.ALL_VALUE)
    public Mono<ResponseEntity> getCheckout(@RequestBody(required = false) CloudEvent<Order> cloudEvent) {
        return Mono.fromSupplier(() -> {
            try {
                publisher.publishEvent(cloudEvent.getData());
                return ResponseEntity.ok("SUCCESS");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @EventListener
    @Async
	void handleTimeEvent(Order order) {
        long duration = (new Date()).getTime() - order.getTimeStamp();
        logger.error("Subscriber received: " + order.getOrderId() + " Diff[ml]: " + duration);
	}
}

@Getter
@Setter
class Order {
    private int orderId;
    private long timeStamp;
}