package com.apex.kafka.Consumer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StringConsumer {
	
	private static final Logger log = LoggerFactory.getLogger(StringConsumer.class);
	
	@KafkaListener(topics = "${TopicName}" , groupId = "apex")
	public void consumeMessage(String message) {
		log.info("Message Recieved --> " + message);
	}

}
