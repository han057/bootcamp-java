package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	public static final String EXCHANGE = "demo.exchange";
	public static final String QUEUE = "demo.queue";
	public static final String ROUTING_KEY = "demo.key";

	@Bean
	DirectExchange demoExchange() {
		return new DirectExchange(EXCHANGE);
	}

	@Bean
	Queue demoQueue() {
		return new Queue(QUEUE, true);
	}

	@Bean
	Binding demoBinding(Queue demoQueue, DirectExchange demoExchange) {
		return BindingBuilder.bind(demoQueue).to(demoExchange).with(ROUTING_KEY);
	}

	@Bean
	MessageConverter messageConverter() {
		return new JacksonJsonMessageConverter();
	}

}
