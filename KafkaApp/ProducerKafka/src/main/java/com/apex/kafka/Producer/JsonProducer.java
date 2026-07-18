package com.apex.kafka.Producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.apex.kafka.Entity.User;

@Service
public class JsonProducer {
	
	private static final Logger log = LoggerFactory.getLogger(JsonProducer.class);
	
	@Value("${TopicNameJSON}")
	private String topicNameJSON;
	
	@Autowired
	private KafkaTemplate<String, User> jsonKafkaTemplate;
	
	public String sendMessage(User data) {
		
		log.info("Message Send --> "+ data.toString());
		
//		Message<User> JsonMssg = MessageBuilder.withPayload(data).setHeader(KafkaHeaders.TOPIC, topicNameJSON).build();
//		
//		jsonKafkaTemplate.send(JsonMssg);
		
		
		jsonKafkaTemplate.send(topicNameJSON, "CustomerID",data);
		return "JSON Message Send Successfully";
	}
	

}
