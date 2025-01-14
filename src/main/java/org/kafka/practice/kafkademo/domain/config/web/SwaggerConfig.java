package org.kafka.practice.kafkademo.domain.config.web;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SwaggerConfig {

    @Value("${web.rest-api.name}")
    private String apiName;

    @Value("${web.rest-api.version}")
    private String apiVersion;

    @Value("${web.rest-api.author-email}")
    private String authorEmail;

    @Bean
    public OpenAPI swaggerOpenApiDefinition() {
        final var contact = new Contact().email(authorEmail);
        final var info = new Info().title(apiName).version(apiVersion).contact(contact);
        return new OpenAPI().info(info);
    }

}