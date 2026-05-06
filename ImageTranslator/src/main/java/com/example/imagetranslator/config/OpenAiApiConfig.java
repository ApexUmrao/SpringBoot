package com.example.imagetranslator.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

@Configuration
public class OpenAiApiConfig {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.chat.options.model:gpt-4o}")
    private String model;

    @Bean
    @Primary
    public RestClient.Builder tolerantRestClientBuilder() {
        ObjectMapper tolerantMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return RestClient.builder()
            .requestFactory(new SimpleClientHttpRequestFactory())
            .messageConverters(converters -> {
                converters.removeIf(c ->
                    c instanceof MappingJackson2HttpMessageConverter);
                converters.add(0,
                    new MappingJackson2HttpMessageConverter(tolerantMapper));
            });
    }

    @Bean
    @Primary
    public OpenAiApi openAiApi(RestClient.Builder tolerantRestClientBuilder) {
        // Try constructor options in this order —
        // hover over OpenAiApi in IntelliJ to see which ones exist in your jar

        // Option 1 — most common in M6:
        return new OpenAiApi(apiKey);

        // Option 2 — if Option 1 compiles but still fails at runtime:
        // return new OpenAiApi("https://api.openai.com", apiKey);

        // Option 3 — with custom RestClient:
        // return new OpenAiApi("https://api.openai.com", apiKey,
        //     tolerantRestClientBuilder, tolerantRestClientBuilder,
        //     ResponseErrorHandler -> {});
    }

    @Bean
    @Primary
    public OpenAiChatModel openAiChatModel(OpenAiApi openAiApi) {
        OpenAiChatOptions options = OpenAiChatOptions.builder()
            .model(model)          // try .withModel(model) if .model() is red
            .maxTokens(4096)       // try .withMaxTokens(4096) if red
            .build();

        // Try constructor options:
        // Option 1:
        return new OpenAiChatModel(openAiApi, options);

        // Option 2 — if Option 1 is red:
        // return new OpenAiChatModel(openAiApi);
    }

    @Bean
    @Primary
    public ChatClient chatClient(OpenAiChatModel model) {
        return ChatClient.builder(model).build();
    }
}