package com.example.imagetranslator.service;

import com.example.imagetranslator.model.TextRegion;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisionService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    @Value("${app.target-language:Hindi}")
    private String targetLanguage;

    public List<TextRegion> detectAndTranslate(byte[] imageBytes, String mimeType) {
        log.info("Calling GPT-4o. Target language: {}", targetLanguage);
        try {
            String prompt = "Detect ALL text in this image. "
                + "Translate each text block to " + targetLanguage + ". "
                + "Return ONLY a raw JSON array, no markdown, no explanation. "
                + "Each element must have exactly: "
                + "{\"original_text\":\"...\","
                + "\"translated_text\":\"...\","
                + "\"x_percent\":0,\"y_percent\":0,"
                + "\"width_percent\":0,\"height_percent\":0}. "
                + "If no text found return [].";

            // ✅ M7 clean API — no Media class needed
            String raw = chatClient.prompt()
                    .user(u -> u
                            .text(prompt)
                            .media(
                                MimeTypeUtils.parseMimeType(mimeType),
                                new ByteArrayResource(imageBytes)
                            )
                    )
                    .call()
                    .content();

            log.debug("GPT-4o response: {}", raw);
            String cleaned = cleanJson(raw);
            List<TextRegion> regions = objectMapper.readValue(
                    cleaned, new TypeReference<List<TextRegion>>() {});
            log.info("Got {} text regions from GPT-4o", regions.size());
            return regions;

        } catch (Exception e) {
            log.error("Vision translation failed: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private String cleanJson(String raw) {
        if (raw == null) return "[]";
        String s = raw.trim();
        if (s.startsWith("```")) {
            s = s.replaceAll("^```[a-zA-Z]*\\n?", "")
                 .replaceAll("```\\s*$", "").trim();
        }
        return s.isEmpty() ? "[]" : s;
    }
}