package com.apex.kafka.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.apex.kafka.Entity.User;

@Service
public class JsonConsumer {
	
	private static final Logger log = LoggerFactory.getLogger(JsonConsumer.class);
	
	public String message;
	
	@KafkaListener(topics = "${TopicNameJSON}" , groupId = "apex")
	public void recieveJson(User data) {
		message = "Message Received from kafka--> " + data.toString();
		log.info("JSON Message Recieved --> " + data.toString());
	}

	public String getMessage() {
		return message;
	}

}