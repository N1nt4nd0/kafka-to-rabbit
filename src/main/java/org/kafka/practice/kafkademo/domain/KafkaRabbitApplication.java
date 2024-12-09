package org.kafka.practice.kafkademo.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// TODO passwords, environment variables to .env file
// TODO global error handler for rabbit

@SpringBootApplication
@EnableTransactionManagement
public class KafkaRabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaRabbitApplication.class, args);
    }

}
