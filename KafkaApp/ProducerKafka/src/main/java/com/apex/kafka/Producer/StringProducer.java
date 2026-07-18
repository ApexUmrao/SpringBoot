package com.apex.kafka.Producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StringProducer {
	
	@Value("${TopicName}")
	private String topicName;
	
	@Autowired
	public KafkaTemplate<String, String> stringKafkaTemplate;
	
	public void sendMessage(String message) {
		stringKafkaTemplate.send(topicName, message);
	}

}
