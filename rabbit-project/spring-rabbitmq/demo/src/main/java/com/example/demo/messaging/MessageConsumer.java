package com.example.demo.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.demo.config.RabbitConfig;

@Component
public class MessageConsumer {

	private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

	@RabbitListener(queues = RabbitConfig.QUEUE)
	public void receive(DemoMessage message) {
		log.info("Mensaje recibido: {}", message);
	}

}
