package com.userservice.user.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String EXCHANGE = "service_exchange";
	public static final String USER_QUEUE = "user_queue";
	public static final String DEPARTMENT_QUEUE = "department_queue";

	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange(EXCHANGE);
	}

	@Bean
	public Queue userQueue() {
		return new Queue(USER_QUEUE,  true, false, false);
	}

	@Bean
	public Queue departmentQueue() {
		return new Queue(DEPARTMENT_QUEUE, true, false, false);
	}

	@Bean
	public Binding userBinding(Queue userQueue, DirectExchange directExchange) {
		return BindingBuilder.bind(userQueue).to(directExchange).with("user_routing_key");
	}

	@Bean
	public Binding departmentBinding(Queue departmentQueue, DirectExchange directExchange) {
		return BindingBuilder.bind(departmentQueue).to(directExchange).with("department_routing_key");
	}
}
