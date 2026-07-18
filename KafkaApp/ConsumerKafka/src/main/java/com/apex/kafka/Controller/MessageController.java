package com.apex.kafka.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apex.kafka.Consumer.JsonConsumer;

@RestController
@RequestMapping("/kafka")
public class MessageController {
	
	@Autowired
	private JsonConsumer jsonConsumer;
	
	@GetMapping("/getMessage")
	public ResponseEntity<String> getMessage(){
		String message = jsonConsumer.getMessage();
		return ResponseEntity.ok(message);
	}

}
