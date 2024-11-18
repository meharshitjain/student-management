package com.userservice.user.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserMessageConsumer {

	@RabbitListener(queues = "user_queue")
	public void handleDepartmentMessage(String message) {
		System.out.println("Received message from RabbitMQ: " + message);
		// Process the message (e.g., update user service database)
	}
}
