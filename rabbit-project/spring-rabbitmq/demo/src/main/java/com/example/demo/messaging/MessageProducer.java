package com.example.demo.messaging;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.config.RabbitConfig;

@Service
public class MessageProducer {

	private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);

	private final RabbitTemplate rabbitTemplate;

	public MessageProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public DemoMessage send(String content) {
		DemoMessage message = new DemoMessage(content, Instant.now());
		rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, message);
		log.info("Mensaje enviado: {}", message);
		return message;
	}

}
