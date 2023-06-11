package com.service;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckoutServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(CheckoutServiceApplication.class);

	public static void main(String[] args) throws InterruptedException{
		String TOPIC_NAME = "orders";
		String PUBSUB_NAME = "orderpubsub";
		DaprClient client = new DaprClientBuilder().build();

		for (int i = 0; i <= 100; i++) {
			int orderId = i;
			Order order = new Order(orderId);

			// Publish an event/message using Dapr PubSub
			client.publishEvent(
					PUBSUB_NAME,
					TOPIC_NAME,
					order).block();
			String format = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
			System.out.println(format);
			logger.info("Published data: " + order.getOrderId());
			TimeUnit.MILLISECONDS.sleep(50);
		}
	}
}

@AllArgsConstructor
@Getter
class Order {
	private int orderId;
}
