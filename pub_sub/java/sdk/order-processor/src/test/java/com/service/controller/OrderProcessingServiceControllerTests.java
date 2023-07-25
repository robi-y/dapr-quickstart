package com.service.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.service.controller.OrderProcessingServiceController;

import org.springframework.beans.factory.annotation.Autowired;


@SpringBootTest
public class OrderProcessingServiceControllerTests {
 
    @Autowired
	private OrderProcessingServiceController controller;

    @Test
	public void contextLoads() {
        assertThat(controller).isNotNull();
	}


}
