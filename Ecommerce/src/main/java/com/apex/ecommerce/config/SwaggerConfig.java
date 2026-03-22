package com.apex.ecommerce.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("JWT Token");

        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("Bearer Authentication");

        return new OpenAPI()
                .info(new Info()
                        .title("Apex E-Commerce API")
                        .version("1.0")
                        .description("This is a Spring Boot Project For E-Commerce")
                        .license(new License().name("Apache 2.0"))
                        .contact(new Contact().name("Apex Uchiha")
                                .email("apex@gmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Apex E-Commerce API Documentation"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                                bearerScheme))
                        .addSecurityItem(securityRequirement);
    }
}
