package com.apex.kafka.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apex.kafka.Entity.User;
import com.apex.kafka.Producer.JsonProducer;
import com.apex.kafka.Producer.StringProducer;

@RestController
@RequestMapping("/kafka")
public class MessageController {
	
	@Autowired
	private StringProducer producer;
	
	@Autowired
	private JsonProducer jsonproducer;
	
	//https//localhost:8081/kafka/publish?message=shivanshu
	@GetMapping("/publish")
	public ResponseEntity<String> publish (@RequestParam("message") String message){
		producer.sendMessage(message);
		return ResponseEntity.ok("Message Send Successfully");
	}
	
	//Body --> { "id": 1, "name" : apex, "mobNo" : 4532534 }
	@PostMapping("/publish")
	public ResponseEntity<String> publish (@RequestBody User data){
		String response = jsonproducer.sendMessage(data);
		return new ResponseEntity<String>(response, HttpStatus.OK);		
	}
	
	

}
