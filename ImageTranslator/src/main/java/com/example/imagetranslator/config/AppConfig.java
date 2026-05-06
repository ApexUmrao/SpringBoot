package com.example.imagetranslator.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // ✅ M7: inject ChatClient.Builder — Spring auto-creates it
    // No need to import any specific model class
	
	// Only keep ObjectMapper here for your own use in VisionService
//    @Bean
//    public ChatClient chatClient(ChatClient.Builder builder) {
//        return builder.build();
//    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}