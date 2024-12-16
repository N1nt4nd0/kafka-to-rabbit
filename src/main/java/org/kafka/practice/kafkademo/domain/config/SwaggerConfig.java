package org.kafka.practice.kafkademo.domain.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swaggerOpenApiConfig() {
        return new OpenAPI().info(new Info().title("Syntegrico Kafka To Rabbit").version("1.0"));
    }

}