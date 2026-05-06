package com.example.imagetranslator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.ai.openai.api-key=test-placeholder",
    "app.target-language=Hindi"
})
class AppTests {
    @Test
    void contextLoads() {}
}
