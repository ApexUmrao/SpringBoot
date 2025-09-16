package com.ai.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/AIChat")
public class AIChatBoxController {
	
	private ChatClient chatClient ;
	
	//Set Constuctor injection for ChatClient
	public AIChatBoxController(ChatClient.Builder chatClient) {
		this.chatClient = chatClient.build();
	}
	
	@GetMapping("/test")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("Test Success for AI Chat Box");
	}
	
	@GetMapping("/{message}")
	public ResponseEntity<String> getMethodName(@PathVariable String message) {
		
		String response = chatClient.prompt(message).call().content();
		return ResponseEntity.ok(response);
		
	}
	

}
